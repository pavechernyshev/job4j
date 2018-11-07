package ru.job4j.tree;

import java.util.*;

public class Tree<E extends Comparable<E>> implements SimpleTree<E> {

    private Node<E> root;
    private int modCount;

    Tree(E root) {
        this.root = new Node<>(root);
    }

    @Override
    public boolean add(E parent, E child) {
        boolean res = false;
        Optional<Node<E>> opt = findBy(parent);
        if (opt.isPresent() && !findBy(child).isPresent()) {
            opt.get().add(new Node<>(child));
            res = true;
            modCount++;
        }
        return res;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int expectedModCount = modCount;
            Queue<E> queue;

            {
                queue = new LinkedList<>();
                queue.add(root.getValue());
                fillQueue(root.leaves());
            }

            private void fillQueue(List<Node<E>> leaves) {
                if (leaves.size() > 0) {
                    for (Node<E> node: leaves) {
                        queue.add(node.getValue());
                        fillQueue(node.leaves());
                    }
                }
            }

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return !queue.isEmpty();
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return queue.poll();
            }
        };
    }
}
