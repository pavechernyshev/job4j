package ru.job4j.bomberman;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface Movable {
    Board.Cell getPosition();
    void setPosition(Board.Cell position);
    void changeDirection(List<Direction.State> blockingDirections);
    Direction.State getDirectionState();
    int getTimeTryLock();
    TimeUnit getTimeUnitForTryLock();
}
