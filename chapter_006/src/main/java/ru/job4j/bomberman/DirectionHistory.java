package ru.job4j.bomberman;

import java.util.LinkedList;
import java.util.Queue;

public class DirectionHistory {
    private final Queue<Direction.State> stepHistory = new LinkedList<>();
    private final Queue<Direction.State> rareHistory = new LinkedList<>();

    private final int historySize;

    public DirectionHistory(int historySize) {
        this.historySize = historySize;
    }

    public void setHistory(Direction.State state) {
        stepHistory.add(state);
        updateRareHistory(state);
        if (stepHistory.size() > historySize) {
            stepHistory.poll();
        }
    }

    private void updateRareHistory(Direction.State state) {
        if (rareHistory.contains(state)) {
            rareHistory.remove(state);
        }
        rareHistory.add(state);
    }

    /**
     * Возвращает самое редко используемое направление, если использовались не все значения
     * будет возвращенно первое никогда не используемое направление
     * @return Direction.State
     */
    public Direction.State getRareDirectionState() {
        Direction.State res = null;
        if (rareHistory.size() < Direction.State.values().length) {
            for (Direction.State state: Direction.State.values()) {
                if (!rareHistory.contains(state)) {
                    res = state;
                    break;
                }
            }
        } else {
            res = rareHistory.peek();
        }
        return res;
    }

    public Queue getHistory() {
        return stepHistory;
    }



}
