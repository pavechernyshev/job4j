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
public class QeenWhite implements Figure {
    private final Cell position;

    public QeenWhite(final Cell position) {
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    @Override
    public Cell[] way(Cell source, Cell dest) {
        int rangeBishop = rangeMoveAsBishop(source, dest);
        int rangeRook = rangeMoveAsRook(source, dest);
        if (rangeBishop == -1 && rangeRook == -1) {
            throw new ImpossibleMoveException("Ферзь не может так ходить");
        }
        return rangeBishop != -1 ? moveAsBishop(source, dest, rangeBishop) : moveAsRook(source, dest, rangeRook);
    }

    private Cell[] moveAsBishop(Cell source, Cell dest, int range) {
        Cell[] steps = new Cell[range];
        int deltaX = source.x > dest.x ? -1 : 1;
        int deltaY = source.y > dest.y ? -1 : 1;
        int x = source.x;
        int y = source.y;
        for (int i = 0; i < range; i++) {
            x += deltaX;
            y += deltaY;
            steps[i] = Cell.values()[8 * x + y];
        }
        return steps;
    }

    private Cell[] moveAsRook(Cell source, Cell dest, int range) {
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

    private int rangeMoveAsBishop(Cell source, Cell dest) {
        int differenceX = source.x > dest.x ? source.x - dest.x : dest.x - source.x;
        int differenceY = source.y > dest.y ? source.y - dest.y : dest.y - source.y;
        return differenceX == differenceY ? differenceX : -1;
    }

    private int rangeMoveAsRook(Cell source, Cell dest) {
        int res = -1;
        if (source.x != dest.x) {
            res = source.x > dest.x ? source.x - dest.x : dest.x - source.x;
        } else if (source.y != dest.y) {
            res = source.y > dest.y ? source.y - dest.y : dest.y - source.y;
        }
        if (dest.x != source.x && dest.y != source.y) {
            res = -1;
        }
        return res;
    }

    @Override
    public Figure copy(Cell dest) {
        return new QeenWhite(dest);
    }
}
