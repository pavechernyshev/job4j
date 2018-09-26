package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenIterator implements Iterator {

    private final int[] array;

    private int position = 0;
    private int evenPosition = -1;

    public EvenIterator(int[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        boolean res = false;
        for (int i = this.position; i < this.array.length; i++) {
            if (array[i] % 2 == 0) {
                res = true;
                this.evenPosition = i;
                break;
            }
        }
        return res;
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.position = this.evenPosition + 1;
        return array[this.evenPosition];
    }
}
