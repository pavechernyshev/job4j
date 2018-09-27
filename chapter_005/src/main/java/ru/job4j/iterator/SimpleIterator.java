package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleIterator<T> implements Iterator<T> {

    private Object[] array;
    private int position = 0;

    public SimpleIterator(Object[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return this.array.length > position;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return (T) this.array[position++];
    }
}
