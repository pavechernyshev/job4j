package ru.job4j.set;

import ru.job4j.list.SimpleArrayList;

import java.util.Iterator;

public class SimpleSet<T> implements Iterable<T> {

    private SimpleArrayList<T> simpleArrayList = new SimpleArrayList<>();

    public void add(T value) {
        boolean allowAdd = true;
        for (T item: simpleArrayList) {
            if (value.equals(item)) {
                allowAdd = false;
                break;
            }
        }
        if (allowAdd) {
            simpleArrayList.add(value);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return simpleArrayList.iterator();
    }
}
