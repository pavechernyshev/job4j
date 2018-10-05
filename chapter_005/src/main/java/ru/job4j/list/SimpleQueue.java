package ru.job4j.list;

import java.util.NoSuchElementException;

/***
 * @author Павел Чернышев (pavel.chernyshev.work@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleQueue<T> {

    SimpleStack<T> stack = new SimpleStack<>();
    SimpleStack<T> queue = new SimpleStack<>();

    public T poll() {
        if (queue.getSize() == 0 && stack.getSize() == 0) {
            throw new NoSuchElementException();
        } else if (queue.getSize() == 0) {
            migrate();
        }
        return queue.poll();
    }

    public void push(T value) {
        stack.push(value);
    }

    private void migrate() {
        final int size = stack.getSize();
        for (int i = 0; i < size; i++) {
            queue.push(stack.poll());
        }
    }
}
