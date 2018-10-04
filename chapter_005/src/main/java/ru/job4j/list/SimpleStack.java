package ru.job4j.list;

import java.util.NoSuchElementException;

/***
 * @author Павел Чернышев (pavel.chernyshev.work@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleStack<T> extends SimpleLinkedList<T> {

    public T poll() {
        if (getSize() == 0) {
            throw new NoSuchElementException();
        }
        T res = get(0);
        delete();
        return res;
    }

    public void push(T value) {
        add(value);
    }
}
