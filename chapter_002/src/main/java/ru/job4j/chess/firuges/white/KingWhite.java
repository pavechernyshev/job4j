package ru.job4j.chess.firuges.white;

import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;
import ru.job4j.chess.firuges.exceptions.ImpossibleMoveException;

/**
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class KingWhite implements Figure {
    private final Cell position;

    public KingWhite(final Cell position) {
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    @Override
    public Cell[] way(Cell source, Cell dest) {
        int stepX = source.x > dest.x ? source.x - dest.x : dest.x - source.x;
        int stepY = source.y > dest.y ? source.y - dest.y : dest.y - source.y;
        if (stepX > 1 || stepY > 1) {
            throw new ImpossibleMoveException("Король ходит только на соседние клетки");
        }
        return new Cell[] {dest };
    }

    @Override
    public Figure copy(Cell dest) {
        return new KingWhite(dest);
    }
}
