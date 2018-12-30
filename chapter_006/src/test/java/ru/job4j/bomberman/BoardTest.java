package ru.job4j.bomberman;

import org.junit.Test;

import static java.lang.Thread.sleep;

public class BoardTest {

    @Test
    public void startup() {
        Board board = new Board(10, Board.Hardly.EASY);
        board.setPlayerCommand(Direction.State.DOWN);
        board.setPlayerCommand(Direction.State.DOWN);
        board.setPlayerCommand(Direction.State.DOWN);
        board.startup();
        while (!board.gameOver()) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        board.shutdown();
    }
}