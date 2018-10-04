package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/***
 * @author Павел Чернышев (pavel.chernyshev.work@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ContainerLinkedList<E> implements Iterable<E>{
    private int size;
    private Node<E> first;
    private Node<E> last;

    /**
     * Метод вставляет в начало списка данные.
     * @param data элемент.
     */
    public void add(E data) {
        Node<E> newLink = new Node<>(data);
        if (first == null) {
            first = newLink;
        } else {
            this.last.next = newLink;
        }
        this.last = newLink;
        this.size++;
    }

    public E get(int index) {
        if (index >= this.getSize()) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> result = this.first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.data;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            final private int size = getSize();
            private int position = 0;
            private Node<E> cursor = first;
            @Override
            public boolean hasNext() {
                if (size != getSize()) {
                    throw new ConcurrentModificationException();
                }
                return position < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E data = cursor.data;
                cursor = cursor.next;
                position++;
                return data;
            }
        };
    }

    /***
     * Класс для хранения данных.
     * @param <E>
     */
    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }
}
