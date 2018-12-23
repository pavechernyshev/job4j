package ru.job4j.bomberman;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;

public class DirectionHistoryTest {

    @Test
    public void rareDirectionState() {
        DirectionHistory directionHistory = new DirectionHistory(10);
        directionHistory.setHistory(Direction.State.DOWN);
        directionHistory.setHistory(Direction.State.UP);
        directionHistory.setHistory(Direction.State.LEFT);
        directionHistory.setHistory(Direction.State.RIGHT);
        assertThat(directionHistory.getRareDirectionState(), Is.is(Direction.State.DOWN));
        directionHistory.setHistory(Direction.State.DOWN);
        assertThat(directionHistory.getRareDirectionState(), Is.is(Direction.State.UP));
        directionHistory.setHistory(Direction.State.LEFT);
        directionHistory.setHistory(Direction.State.DOWN);
        assertThat(directionHistory.getRareDirectionState(), Is.is(Direction.State.UP));
    }

    @Test
    public void getHistory() {
        DirectionHistory directionHistory = new DirectionHistory(2);
        directionHistory.setHistory(Direction.State.UP);
        directionHistory.setHistory(Direction.State.DOWN);
        directionHistory.setHistory(Direction.State.LEFT);
        Queue<Direction.State> expected = new LinkedList<>();
        expected.add(Direction.State.DOWN);
        expected.add(Direction.State.LEFT);
        assertThat(directionHistory.getHistory(), Is.is(expected));
    }
}