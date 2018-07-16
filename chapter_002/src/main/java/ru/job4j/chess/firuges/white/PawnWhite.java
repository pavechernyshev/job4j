package ru.job4j.chess.firuges.white;

import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;
import ru.job4j.chess.firuges.exceptions.ImpossibleMoveException;

/**
 * //TODO add comments.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class PawnWhite implements Figure {
    private final Cell position;

    public PawnWhite(final Cell position) {
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    @Override
    public Cell[] way(Cell source, Cell dest) {
        Cell[] steps;
        if (dest.y < 0 || dest.y > 7 || dest.x != source.x || source.y != dest.y - 1) {
            throw new ImpossibleMoveException();
        }
        return new Cell[] {dest };
    }

    @Override
    public Figure copy(Cell dest) {
        return new PawnWhite(dest);
    }
}
