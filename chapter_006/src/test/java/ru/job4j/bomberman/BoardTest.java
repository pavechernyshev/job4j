package ru.job4j.bomberman;

import org.junit.Test;

import static java.lang.Thread.sleep;

public class BoardTest {

    @Test
    public void startup() {
        Board board = new Board(4);
        board.startup();
        try {
            //sleep(15000);
            sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        board.shutdown();
    }
}