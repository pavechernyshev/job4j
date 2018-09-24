package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Converter {
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            Iterator<Integer> sub;
            boolean firstFill = false;
            @Override
            public boolean hasNext() {
                init();
                return firstFill && (it.hasNext() || sub != null && sub.hasNext());
            }

            @Override
            public Integer next() {
                init();
                if (sub == null && !it.hasNext()) {
                    throw new NoSuchElementException();
                } else if (sub == null) {
                    sub = it.next();
                }
                if (!sub.hasNext() && it.hasNext()) {
                    sub = it.next();
                }
                return sub.next();
            }

            private void init() {
                if (sub == null && it.hasNext()) {
                    sub = it.next();
                    if (sub.hasNext()) {
                        firstFill = true;
                    }
                }
            }
        };
    }
}
