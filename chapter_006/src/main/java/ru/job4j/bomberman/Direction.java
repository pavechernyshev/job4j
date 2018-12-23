package ru.job4j.bomberman;

public class Direction {
    public enum State {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private State state;

    Direction(State state) {
        this.state = state;
    }

    public State getRevertDirectionState(State state) {
        State res;
        switch (state) {
            case UP: res = State.DOWN; break;
            case DOWN: res = State.UP; break;
            case LEFT: res = State.RIGHT; break;
            case RIGHT: res = State.LEFT; break;
            default: res = null;
        }
        return res;
    }

    public State getRevertDirectionState() {
        return getRevertDirectionState(this.state);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
