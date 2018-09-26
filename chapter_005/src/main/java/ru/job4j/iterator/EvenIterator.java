package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenIterator implements Iterator {

    private final int[] array;

    private int position = 0;

    public EvenIterator(int[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return evenPosition() != -1;
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int evenPosition = evenPosition();
        this.position = evenPosition + 1;
        return array[evenPosition];
    }

    private int evenPosition() {
        int res = -1;
        for (int i = position; i < array.length; i++) {
            if (array[i] % 2 == 0) {
                res = i;
                break;
            }
        }
        return res;
    }
}
