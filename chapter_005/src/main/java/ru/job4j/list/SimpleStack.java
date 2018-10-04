package ru.job4j.list;

import java.util.NoSuchElementException;

/***
 * @author Павел Чернышев (pavel.chernyshev.work@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleStack<T> {

    private SimpleLinkedList<T> simpleLinkedList = new SimpleLinkedList<>();

    public T poll() {
        if (simpleLinkedList.getSize() == 0) {
            throw new NoSuchElementException();
        }
        return simpleLinkedList.delete();
    }

    public void push(T value) {
        simpleLinkedList.add(value);
    }

    public int getSize() {
        return simpleLinkedList.getSize();
    }
}
