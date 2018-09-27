package ru.job4j.generics;

import ru.job4j.iterator.SimpleIterator;

import java.util.Iterator;

public class SimpleArray<T> implements Iterable<T> {

    private Object[] array;
    private int position = 0;
    private int size = 0;

    public SimpleArray(int size) {
        this.size = size;
        this.array = new Object[size];
    }

    public void add(T model) {
        this.array[position++] = model;
    }

    public void set(int index, T model) {
        this.array[index] = model;
    }

    public void delete(int index) {
        this.array[index] = null;
    }

    public T get(int index) {
        return (T) this.array[index];
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleIterator<T>(this.array);
    }
}
