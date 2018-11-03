package ru.job4j.map;

import java.util.*;

public class MyHashMap<K, V> implements Iterable<Map.Entry> {

    private Node<K, V>[] table;
    private int capacity;
    private int threshold;
    private int count;
    private float loadFactor;
    private int modCount;

    MyHashMap() {
        this.capacity = 10;
        this.loadFactor = 0.5f;
        this.threshold = (int) (this.capacity * loadFactor);
        this.table = (Node<K, V>[]) new Node[this.capacity];
    }

    public V get(K key) {
        V res = null;
        int index = getIndex(key.hashCode());
        if (this.table[index] != null) {
            res = (V) this.table[index].getValue();
        }
        return res;
    }

    public boolean insert(K key, V value) {
        boolean res = false;
        int keyHash = key.hashCode();
        int index = getIndex(keyHash);
        if (this.table[index] == null) {
            this.table[index] = new Node<>(keyHash, key, value);
            if (this.count++ == threshold) {
                refresh();
            }
            res = true;
        }
        return res;
    }

    public boolean delete(K key) {
        boolean res = false;
        int index = getIndex(key.hashCode());
        if (this.table[index] != null) {
            this.table[index] = null;
            this.count--;
            res = true;
        }
        return res;
    }

    public int getCapacity() {
        return this.capacity;
    }

    private int getIndex(int hash) {
        return (hash & 0x7FFFFFFF) % this.table.length;
    }

    private void refresh() {
        this.modCount++;
        this.capacity = this.capacity * 2;
        this.threshold = (int) (this.capacity * this.loadFactor);
        Node<?, ?>[] oldTab = this.table;
        this.table = (Node<K, V>[]) new Node[this.capacity];
        for (Node<K, V> e: (Node<K, V>[]) oldTab) {
            if (e != null) {
                this.table[getIndex(e.hash)] = e;
            }
        }
    }

    @Override
    public Iterator<Map.Entry> iterator() {
        return new Iterator<Map.Entry>() {
            private final int expectedModCount = modCount;
            private int position;
            private int innerCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return this.innerCount < count;
            }

            @Override
            public MyHashMap.Node next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                while (table[position] == null) {
                    position++;
                }
                innerCount++;
                return table[position++];
            }
        };
    }


    class Node<K, V> implements Map.Entry<K, V> {
        private final int hash;
        private final K key;
        private V value;

        Node(int hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<K, V> e = (Map.Entry<K, V>) o;
            return (key == null ? e.getKey() == null : key.equals(e.getKey()))
                    && (value == null ? e.getValue() == null : value.equals(e.getValue()));
        }

        @Override
        public int hashCode() {
            return hash ^ Objects.hashCode(value);
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            if (value == null) {
                throw new NullPointerException();
            }
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }
}
