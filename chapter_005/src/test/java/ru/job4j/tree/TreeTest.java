package ru.job4j.tree;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TreeTest {
    @Test
    public void when6ElFindLastThen6() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(6).isPresent(),
                is(true)
        );
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }

    @Test
    public void whenAddDuplicateThenValueUnic() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(4, 5);
        tree.add(4, 5);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(6).isPresent(),
                is(true)
        );
        int dupCount = 0;
        for (Integer i: tree) {
            if (i == 5) {
                dupCount++;
            }
        }
        assertThat(dupCount, is(1));
    }

    @Test
    public void when6ElFindLastThenTreeIsNotBinary() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.isBinary(),
                is(false)
        );
    }

    @Test
    public void whenCreateBinaryTreeThenIsBinaryIsTrue() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 5);
        tree.add(2, 6);
        tree.add(3, 7);
        tree.add(3, 8);
        tree.add(5, 9);
        tree.add(5, 10);
        tree.add(6, 11);
        tree.add(7, 12);
        tree.add(7, 13);
        tree.add(8, 14);
        tree.add(8, 15);
        assertThat(
                tree.isBinary(),
                is(true)
        );
    }

    @Test
    public void whenCreateNotBinaryTreeThenIsBinaryIsFalse() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 5);
        tree.add(2, 6);
        tree.add(3, 7);
        tree.add(3, 8);
        tree.add(5, 9);
        tree.add(5, 10);
        tree.add(5, 16); //not binary
        tree.add(6, 11);
        tree.add(7, 12);
        tree.add(7, 13);
        tree.add(8, 14);
        tree.add(8, 15);
        assertThat(
                tree.isBinary(),
                is(false)
        );
    }
}