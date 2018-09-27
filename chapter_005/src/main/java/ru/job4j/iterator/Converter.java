package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Converter {
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            Iterator<Integer> sub = it.next();
            @Override
            public boolean hasNext() {
                boolean result = false;
                while (true) {
                    if (!sub.hasNext() && !it.hasNext()) {
                        break;
                    }
                    if (!sub.hasNext() && it.hasNext()) {
                        sub = it.next();
                    }
                    if (sub.hasNext()) {
                        result = true;
                        break;
                    }
                }
                return result;
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return sub.next();
            }
        };
    }
}
