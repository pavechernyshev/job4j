package ru.job4j.bomberman;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Board {
    private final ReentrantLock[][] board;
    private final int boardSize;
    private final int firstRowIndex = 0;
    private final int firstColIndex = 0;
    private final int lastRowIndex;
    private final int lastColIndex;
    private final HashSet<Block> blocks = new HashSet<>();
    private final Hardly hardly;
    private final Player player = new Player(new Cell(0, 0));


    Board(int boardSize, Hardly hardly) {
        boardSize = boardSize > 1 ? boardSize : 10;
        this.board = new ReentrantLock[boardSize][boardSize];
        this.boardSize = boardSize;
        this.lastRowIndex = boardSize - 1;
        this.lastColIndex = boardSize - 1;
        this.hardly = hardly;
    }

    private void fillBoard() {
        for (int rowIndex = firstRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
            for (int colIndex = firstColIndex; colIndex <= lastColIndex; colIndex++) {
                board[rowIndex][colIndex] = new ReentrantLock();
            }
        }
    }

    private void generateBlocks() {
        int maxBlocksCount = this.boardSize;
        for (int i = 0; i < maxBlocksCount; i++) {
            Cell blockPos = getRandomPosition();
            this.blocks.add(new Block(blockPos));
        }
    }

    private Cell getRandomPosition() {
        Cell res = new Cell(randomRange(0, lastColIndex), randomRange(0, lastRowIndex));
        if (getReservedCells().contains(res)) {
            res = getRandomPosition();
        }
        return res;
    }

    private int randomRange(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    private List<Cell> getReservedCells() {
        List<Cell> reservedCells = new LinkedList<>();
        reservedCells.add(new Cell(firstColIndex, firstRowIndex));
        reservedCells.add(new Cell(firstColIndex, lastRowIndex));
        reservedCells.add(new Cell(lastColIndex, firstColIndex));
        reservedCells.add(new Cell(lastColIndex, lastColIndex));
        return reservedCells;
    }

    public void startup() {
        fillBoard();
        player.start();
        Thread easyEnemy = new Enemy(new Cell(firstColIndex, lastRowIndex), Direction.State.LEFT, "easyEnemy");
        Thread middleEnemy = new Enemy(new Cell(lastColIndex, firstColIndex), Direction.State.UP, "middleEnemy");
        Thread hardlyEnemy = new Enemy(new Cell(lastColIndex, lastRowIndex), Direction.State.RIGHT, "hardlyEnemy");
        easyEnemy.setDaemon(true);
        middleEnemy.setDaemon(true);
        hardlyEnemy.setDaemon(true);
        switch (hardly) {
            case MIDDLE: middleEnemy.start(); break;
            case HARD: middleEnemy.start(); hardlyEnemy.start(); break;
            default: easyEnemy.start();
        }

    }

    public void shutdown() {
        player.interrupt();
    }

    public void move(Movable moveObj, Cell source, Cell dist) {
        //System.out.println(String.format("Движение с %s к %s", source, dist));
        try {
            if (this.board[dist.getX()][dist.getY()].tryLock(500, TimeUnit.MILLISECONDS)) {
                this.board[dist.getX()][dist.getY()].lock();
                if (this.board[source.getX()][source.getY()].isLocked()) {
                    this.board[source.getX()][source.getY()].unlock();
                }
                moveObj.setPosition(dist);
            } else {
                List<Direction.State> blockedDirections = getBlockedDirections(moveObj.getPosition());
                blockedDirections.add(moveObj.getDirectionState());
                moveObj.changeDirection(blockedDirections);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод проверяет соседние ячейки на наличие блоков и возвращает список направлений,
     * по которым в соседней ячеке располагается блок
     */
    public List<Direction.State> getDirectionsWithBlocks(Cell pos) {
        List<Direction.State> directionsWithBlocks = new LinkedList<>();
        for (Direction.State state: Direction.State.values()) {
            Cell nextCellByDir = getNextCellByDirection(state, pos);
            if (nextCellByDir != null && blocks.contains(new Block(nextCellByDir))) {
                directionsWithBlocks.add(state);
            }
        }
        return directionsWithBlocks;
    }

    public void setPlayerCommand(Direction.State command) {
        this.player.setCommand(command);
    }

    /**
     * Возвращает новую позицию относительно переданно по переданному направлению
     * если ячека не может быть полченна, например pos это координаты на краю доски
     * и по направлению дальше ничего нет, в таком случае возвращается null
     * @param state направление
     * @param pos позиция
     * @return Cell
     */
    private Cell getNextCellByDirection(Direction.State state, Cell pos) {
        Cell res = null;
        if (state == Direction.State.DOWN && !cellOnDownBoarder(pos)) {
            res = new Cell(pos.getX(), pos.getY() + 1);
        }
        if (state == Direction.State.UP && !cellOnUpBorder(pos)) {
            res = new Cell(pos.getX(), pos.getY() - 1);
        }
        if (state == Direction.State.LEFT && !cellOnLeftBorder(pos)) {
            res = new Cell(pos.getX() - 1, pos.getY());
        }
        if (state == Direction.State.RIGHT && !cellOnRightBorder(pos)) {
            res = new Cell(pos.getX() + 1, pos.getY());
        }
        return res;
    }

    private boolean cellOnDownBoarder(Cell cell) {
        return cell.getY() == lastRowIndex;
    }

    private boolean cellOnUpBorder(Cell cell) {
        return cell.getY() == firstRowIndex;
    }

    private boolean cellOnLeftBorder(Cell cell) {
        return cell.getX() == firstColIndex;
    }

    private boolean cellOnRightBorder(Cell cell) {
        return cell.getX() == lastColIndex;
    }

    /**
     * метод получает заблокированне направления
     * @param position позиция относительно которой определяются заблокированные направления
     * @return List<Direction.State>
     */
    private List<Direction.State> getBlockedDirections(Cell position) {
        List<Direction.State> blockedDirStats = getDirectionsWithBlocks(position);
        if (cellOnLeftBorder(position)) {
            blockedDirStats.add(Direction.State.LEFT);
        }
        if (cellOnRightBorder(position)) {
            blockedDirStats.add(Direction.State.RIGHT);
        }
        if (cellOnUpBorder(position)) {
            blockedDirStats.add(Direction.State.UP);
        }
        if (cellOnDownBoarder(position)) {
            blockedDirStats.add(Direction.State.DOWN);
        }
        return blockedDirStats;
    }

    /**
     * метод проверяет может ли объект сохранять текущее напрваление
     */
    private boolean canStateCurDirection(Cell position, Direction.State directionState) {
        List<Direction.State> blockedDirections = getBlockedDirections(position);
        return !blockedDirections.contains(directionState);
    }

    public enum Hardly {
        EASY,
        MIDDLE,
        HARD
    }

    class Cell {
        private final int x;
        private final int y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Cell cell = (Cell) o;
            return x == cell.x && y == cell.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return String.format("x: %s; y: %s", getX(), getY());
        }
    }

    class Enemy extends Thread implements Movable {
        private final Direction direction;
        private Cell position;

        public Direction.State getDirectionState() {
            return direction.getState();
        }

        public Cell getPosition() {
            return position;
        }

        public void setPosition(Cell position) {
            this.position = position;
        }

        /**
         * метод меняет направление объекта
         */
        public void changeDirection() {
            List<Direction.State> blockedDirections = getBlockedDirections(getPosition());
            changeDirection(blockedDirections);
        }

        public void changeDirection(List<Direction.State> blockedDirections) {
            Direction.State newDirectionState = direction.getRareDirectionState();
            if (blockedDirections.contains(newDirectionState)) {
                for (Direction.State state: Direction.State.values()) {
                    if (!blockedDirections.contains(state)) {
                        newDirectionState = state;
                    }
                }
            }
            this.direction.setState(newDirectionState);
        }

        Enemy(Cell startPosition, Direction.State defaultDirection, String name) {
            this.setName(name);
            position = startPosition;
            direction = new Direction(defaultDirection);
        }

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                try {
                    if (!canStateCurDirection(this.position, this.direction.getState())) {
                        changeDirection();
                    }
                    move(this, getPosition(), getNextCellByDirection(direction.getState(), position));
                    sleep(1000);
                    System.out.println(this.getName() + " " + position);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    this.interrupt();
                }
            }
        }
    }

    class Player extends Thread implements Movable {
        private final Queue<Direction.State> commands;
        private final DirectionHistory directionHistory;
        private final Direction direction = new Direction(Direction.State.DOWN);
        private Cell position;

        Player(Cell startPosition) {
            this.setName("PLAYER");
            this.position = startPosition;
            commands = new LinkedList<>();
            directionHistory = new DirectionHistory(100);
        }

        public void setCommand(Direction.State command) {
            commands.add(command);
            directionHistory.setHistory(command);
        }

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                try {
                    if (this.commands.size() > 0) {
                        this.direction.setState(this.commands.poll());
                        if (canStateCurDirection(this.position, this.direction.getState())) {
                            move(this, getPosition(), getNextCellByDirection(direction.getState(), position));
                            sleep(1000);
                            System.out.println(this.getName() + " " + this.position);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    this.interrupt();
                }
            }
        }

        @Override
        public Cell getPosition() {
            return this.position;
        }

        @Override
        public void setPosition(Cell position) {
            this.position = position;
        }

        @Override
        public void changeDirection(List<Direction.State> blockingDirections) {
            run();
        }

        @Override
        public Direction.State getDirectionState() {
            return this.direction.getState();
        }
    }
}
