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
public class RookWhite implements Figure {
    private final Cell position;

    public RookWhite(final Cell position) {
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int range = range(source, dest);
        if (dest.x != source.x && dest.y != source.y) {
            throw new ImpossibleMoveException("Ладья ходит по прямой");
        }
        if (range == -1) {
            throw new ImpossibleMoveException("Не удалось выяснить растояние хода для ладьи");
        }
        Cell[] steps = new Cell[range];
        int deltaX = source.x == dest.x ? 0 : source.x > dest.x ? -1 : 1;
        int deltaY = source.y == dest.y ? 0 : source.y > dest.y ? -1 : 1;
        int x = source.x;
        int y = source.y;
        for (int index = 0; index < range; index++) {
            x += deltaX;
            y += deltaY;
            steps[index] = Cell.values()[8 * x + y];
        }
        return steps;
    }

    private int range(Cell source, Cell dest) {
        int res = -1;
        if (source.x != dest.x) {
            res = source.x > dest.x ? source.x - dest.x : dest.x - source.x;
        } else if (source.y != dest.y) {
            res = source.y > dest.y ? source.y - dest.y : dest.y - source.y;
        }
        return res;
    }

    @Override
    public Figure copy(Cell dest) {
        return new RookWhite(dest);
    }
}
