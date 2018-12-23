package ru.job4j.bomberman;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Board {
    private final ReentrantLock[][] board;
    private final int firstRowIndex = 0;
    private final int firstColIndex = 0;
    private final int lastRowIndex;
    private final int lastColIndex;
    private final Player player;


    Board(int boardSize) {
        boardSize = boardSize > 1 ? boardSize : 10;
        this.board = new ReentrantLock[boardSize][boardSize];
        this.lastRowIndex = boardSize - 1;
        this.lastColIndex = boardSize - 1;
        this.player = new Player();
    }

    private void fillBoard() {
        for (int rowIndex = firstRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
            for (int colIndex = firstColIndex; colIndex <= lastColIndex; colIndex++) {
                board[rowIndex][colIndex] = new ReentrantLock();
            }
        }
    }

    public void startup() {
        fillBoard();
        player.start();
    }

    public void shutdown() {
        player.interrupt();
    }

    public void move(Cell source, Cell dist) {
        //System.out.println(String.format("Движение с %s к %s", source, dist));
        try {
            if (this.board[dist.getX()][dist.getY()].tryLock(500, TimeUnit.MILLISECONDS)) {
                this.board[dist.getX()][dist.getY()].lock();
                if (this.board[source.getX()][source.getY()].isLocked()) {
                    this.board[source.getX()][source.getY()].unlock();
                }
                player.setPosition(dist);
            } else {
                List<Direction.State> blockedDirections = player.getBlockedDirections(player.getPosition());
                blockedDirections.add(player.getDirection().getState());
                player.changeDirection(blockedDirections);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        public String toString() {
            return String.format("x: %s; y: %s", getX(), getY());
        }
    }

    class Player extends Thread {
        private final Direction direction;
        private Cell position;

        public Direction getDirection() {
            return direction;
        }

        public Cell getPosition() {
            return position;
        }

        public void setPosition(Cell position) {
            this.position = position;
        }

        /**
         * метод определения следующей ячейки
          */
        private Cell getDistCell() {
            Cell dist;
            switch (direction.getState()) {
                case DOWN: dist = new Cell(position.getX(), position.getY() + 1); break;
                case UP: dist = new Cell(position.getX(), position.getY() - 1); break;
                case RIGHT: dist = new Cell(position.getX() + 1, position.getY()); break;
                case LEFT: dist = new Cell(position.getX() - 1, position.getY()); break;
                default: dist = position;
            }
            return dist;
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


        /**
         * метод получает заблокированне направления
         * @param position позиция относительно которой определяются заблокированные направления
         * @return List<Direction.State>
         */
        public List<Direction.State> getBlockedDirections(Cell position) {
            List<Direction.State> blockedDirStats = new LinkedList<>();
            if (position.x == firstColIndex) {
                blockedDirStats.add(Direction.State.LEFT);
            }
            if (position.x == lastColIndex) {
                blockedDirStats.add(Direction.State.RIGHT);
            }
            if (position.y == firstRowIndex) {
                blockedDirStats.add(Direction.State.UP);
            }
            if (position.y == lastRowIndex) {
                blockedDirStats.add(Direction.State.DOWN);
            }
            return blockedDirStats;
        }

        /**
         * метод проверяет может ли объект сохранять текущее напрваление
         */
        private boolean canStateCurDirection() {
            List<Direction.State> blockedDirections = getBlockedDirections(this.position);
            return !blockedDirections.contains(direction.getState());
        }

        Player() {
            position = new Cell(0, 0);
            Direction.State startDirectionState = Direction.State.DOWN;
            direction = new Direction(startDirectionState);
        }

        @Override
        public void run() {
            while (!this.isInterrupted()) {
                try {
                    if (!canStateCurDirection()) {
                        changeDirection();
                    }
                    move(getPosition(), getDistCell());
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    this.interrupt();
                }
            }
        }
    }
}
