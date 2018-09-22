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
        return this.position != -1 && nextPosition(this.position) != -1;
    }

    @Override
    public Object next() {
        if (this.array.length == 0 || this.position == -1) {
            throw new NoSuchElementException();
        }
        int curIndex = nextPosition(this.position);
        if (curIndex == -1) {
            throw new NoSuchElementException();
        }
        this.position = nextPosition(curIndex + 1);
        return array[curIndex];
    }

    private int nextPosition(int startPos) {
        int res = -1;
        for (int i = startPos; i < this.array.length; i++) {
            if (this.array[i] % 2 == 0) {
                res = i;
                break;
            }
        }
        return res;
    }
}
