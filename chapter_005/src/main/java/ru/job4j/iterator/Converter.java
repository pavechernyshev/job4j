package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Converter {
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            Iterator<Integer> sub = it.next();
            @Override
            public boolean hasNext() {
                return sub.hasNext();
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Integer res = sub.next();
                if (!hasNext() && it.hasNext()) {
                    sub = it.next();
                }
                return res;
            }
        };
    }
}
