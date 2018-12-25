package ru.job4j.bomberman;

import java.util.Objects;

public class Block {
    private Board.Cell position;

    Block(Board.Cell position) {
        this.position = position;
    }

    public Board.Cell getPos() {
        return this.position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Block block = (Block) o;
        return Objects.equals(position, block.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
