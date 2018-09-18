package ru.job4j.chess;

import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;
import ru.job4j.chess.firuges.exceptions.ImpossibleMoveException;
import ru.job4j.chess.firuges.exceptions.OccupiedWayException;
import ru.job4j.chess.firuges.exceptions.FigureNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * //TODO add comments.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Logic {
    private final List<Figure> figures = new ArrayList<>(32);
    private int index = 0;

    public void add(Figure figure) {
        this.figures.add(this.index++, figure);
    }

    public boolean move(Cell source, Cell dest) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        int index = this.findBy(source);
        if (index == -1) {
            throw new FigureNotFoundException();
        }
        Cell[] steps = this.figures.get(index).way(source, dest);
        for (Cell cell: steps) {
            if (!checkDest(cell)) {
                throw new OccupiedWayException("Путь заблокирован");
            }
        }
        if (steps.length > 0 && steps[steps.length - 1].equals(dest)) {
            this.figures.add(index, this.figures.get(index).copy(dest));
        } else {
            throw new ImpossibleMoveException();
        }
        return true;
    }

    public boolean checkDest(Cell dest) {
        final boolean[] res = {true};
        this.figures.forEach(f -> {
            if (f.position().equals(dest)) {
                res[0] = false;
            }
        });
        return res[0];
    }

    public void clean() {
        this.figures.removeIf(i -> true);
    }

    private int findBy(Cell cell) {
        final int[] res = {-1};
        this.figures.forEach(f -> {
            if (f != null && f.position().equals(cell)) {
                res[0] = this.figures.indexOf(f);
            }
        });
        return res[0];
    }
}
