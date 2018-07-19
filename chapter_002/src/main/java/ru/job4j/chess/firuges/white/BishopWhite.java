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
public class BishopWhite implements Figure {
    private final Cell position;

    public BishopWhite(final Cell position) {
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    @Override
    public Cell[] way(Cell source, Cell dest) {
        int range = rangeMove(source, dest);
        if (range == -1) {
            throw new ImpossibleMoveException("Слон ходит только по диагонали");
        }
        Cell[] steps = new Cell[range];
        int deltaX = source.x > dest.x ? -1 : 1;
        int deltaY = source.y > dest.y ? -1 : 1;
        int x = source.x;
        int y = source.y;
        for (int i = 0; i < range; i++) {
            steps[i] = Cell.values()[8 * (x = x + deltaX) + (y = y + deltaY)];
        }
        return steps;
    }

    private int rangeMove(Cell source, Cell dest) {
        int differenceX = source.x > dest.x ? source.x - dest.x : dest.x - source.x;
        int differenceY = source.y > dest.y ? source.y - dest.y : dest.y - source.y;
        return differenceX == differenceY ? differenceX : -1;
    }

    @Override
    public Figure copy(Cell dest) {
        return new BishopWhite(dest);
    }
}
