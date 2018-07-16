package ru.job4j.chess.firuges.exceptions;

public class FigureNotFoundException extends RuntimeException {
    public FigureNotFoundException(String msg) {
        super(msg);
    }

    public FigureNotFoundException() {
        super("Фигура не найдена");
    }
}
