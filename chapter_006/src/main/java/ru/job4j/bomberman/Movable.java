package ru.job4j.bomberman;

import java.util.List;

public interface Movable {
    Board.Cell getPosition();
    void setPosition(Board.Cell position);
    void changeDirection(List<Direction.State> blockingDirections);
    Direction.State getDirectionState();
}
