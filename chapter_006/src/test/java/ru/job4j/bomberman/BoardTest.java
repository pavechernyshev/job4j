package ru.job4j.bomberman;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;

import static java.lang.Thread.sleep;
import static junit.framework.TestCase.assertTrue;

public class BoardTest {

    @Test
    public void startup() {
        Board board = new Board(10, Board.Hardly.EASY);
        board.setPlayerCommand(Direction.State.RIGHT);
        board.setPlayerCommand(Direction.State.RIGHT);
        board.setPlayerCommand(Direction.State.DOWN);
        board.setPlayerCommand(Direction.State.DOWN);
        board.setPlayerCommand(Direction.State.LEFT);
        board.setPlayerCommand(Direction.State.UP);
        board.startup();
        try {
            sleep(15000);
            sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        board.shutdown();
    }
}