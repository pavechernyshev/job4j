package ru.job4j.list;

import java.util.*;
import java.util.function.Consumer;

/***
 * @author Павел Чернышев (pavel.chernyshev.work@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleArrayList<E> implements Iterable<E> {

    private Object[] elementData;
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final int DEFAULT_CAPACITY = 10;
    private int modCount = 0;
    private int size = 0;

    SimpleArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
    }

    SimpleArrayList(int size) {
        size = size < 0 ? DEFAULT_CAPACITY : size;
        this.elementData = new Object[size];
    }

    private void ensureCapacityInternal(int capacity) {
        if (capacity > this.elementData.length) {
            grow();
        }
    }

    private void grow() {
        this.modCount++;
        this.elementData = Arrays.copyOf(this.elementData, this.elementData.length * 2);
    }

    public void add(E value) {
        ensureCapacityInternal(size + 1);
        this.elementData[size++] = value;
    }

    public E get(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        return (E) elementData[index];
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private final int expectedModCount = modCount;
            private int position = 0;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return position < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) elementData[position++];
            }
        };
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        final int expectedModCount = modCount;
        @SuppressWarnings("unchecked")
        final E[] elementData = (E[]) this.elementData;
        final int size = this.size;
        for (int i = 0; modCount == expectedModCount && i < size; i++) {
            action.accept(elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }
}
