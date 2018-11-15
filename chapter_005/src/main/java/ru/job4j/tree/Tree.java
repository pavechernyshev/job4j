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

    public boolean isBinary() {
        return root != null && checkBinary(root);
    }

    private boolean checkBinary(Node<E> node) {
        boolean res = true;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(node);
        while (queue.size() > 0) {
            List<Node<E>> leaves = queue.poll().leaves();
            if (leaves.size() > 2) {
                res = false;
                break;
            }
            queue.addAll(leaves);
        }
        return res;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int expectedModCount = modCount;
            Queue<Node<E>> queue;

            {
                queue = new LinkedList<>();
                if (root != null) {
                    queue.add(root);
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
                Node<E> resNode = queue.poll();
                queue.addAll(resNode.leaves());
                return resNode.getValue();
            }
        };
    }


}
