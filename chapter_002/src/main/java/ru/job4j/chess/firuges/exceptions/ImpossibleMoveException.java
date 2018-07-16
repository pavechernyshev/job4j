package ru.job4j.chess.firuges.exceptions;

public class ImpossibleMoveException extends RuntimeException {
    public ImpossibleMoveException(String msg) {
        super(msg);
    }
    public ImpossibleMoveException() {
        super("Фигура не может туда пойти.");
    }
}
