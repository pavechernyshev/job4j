package ru.job4j.list;

/***
 * @author Павел Чернышев (pavel.chernyshev.work@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Node<T> {
    T value;
    Node<T> next;

    Node() {

    }

    Node(T value) {
        this.value = value;
    }
}
