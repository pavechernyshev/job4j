package ru.job4j.chess.firuges.exceptions;

public class OccupiedWayException extends RuntimeException {
    public OccupiedWayException(String msg) {
        super(msg);
    }
    public OccupiedWayException() {
        super("Ячейка занята");
    }
}
