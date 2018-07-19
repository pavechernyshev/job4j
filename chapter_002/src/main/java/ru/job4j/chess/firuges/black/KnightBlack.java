package ru.job4j.chess.firuges.black;

import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;
import ru.job4j.chess.firuges.exceptions.ImpossibleMoveException;

/**
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class KnightBlack implements Figure {
    private final Cell position;

    public KnightBlack(final Cell position) {
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    @Override
    public Cell[] way(Cell source, Cell dest) {
        if (source.x == dest.x + 1 || source.x == dest.x - 1) {
            int stepY = source.y > dest.y ? source.y - dest.y : dest.y - source.y;
            if (stepY != 2) {
                throw new ImpossibleMoveException("Конь ходит буквой Г");
            }
        } else if (source.y == dest.y + 1 || source.y == dest.y - 1) {
            int stepX = source.x > dest.x ? source.x - dest.x : dest.x - source.x;
            if (stepX != 2) {
                throw new ImpossibleMoveException("Конь ходит буквой Г");
            }
        } else {
            throw new ImpossibleMoveException("Конь ходит буквой Г");
        }
        return new Cell[] {dest };
    }

    @Override
    public Figure copy(Cell dest) {
        return new KnightBlack(dest);
    }
}
