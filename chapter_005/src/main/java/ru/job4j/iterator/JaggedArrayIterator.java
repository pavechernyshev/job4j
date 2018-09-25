package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class JaggedArrayIterator implements Iterator {
    private final int[][] array;
    private int first = 0;
    private int second = 0;

    public JaggedArrayIterator(int[][] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return array.length > first && array[first].length > second;
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int res = array[first][second];
        if (array[first].length - 1 > second) {
            second++;
        } else {
            second = 0;
            first++;
        }
        return res;
    }
}
