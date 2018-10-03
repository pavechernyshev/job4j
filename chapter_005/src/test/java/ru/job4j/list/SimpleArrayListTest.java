package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class SimpleArrayListTest {
    private SimpleArrayList<Integer> sal = new SimpleArrayList<>();

    @Before
    public void fill() {
        sal.add(1);
        sal.add(2);
        sal.add(3);
    }

    @Test
    public void add() {
        for (int i = 4; i <= 100; i++) {
            sal.add(i);
        }
        assertThat(sal.get(99), is(100));
    }

    @Test
    public void get() {
        assertThat(sal.get(0), is(1));
        assertThat(sal.get(1), is(2));
        assertThat(sal.get(2), is(3));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void whenOutOfSizeGet() {
        sal.get(3);
    }

    @Test
    public void iterator() {
        Iterator<Integer> iterator = sal.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(3));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void forEach() {
        int[] expected = {1, 2, 3};
        ArrayList<Integer> al = new ArrayList<>();
        for (Integer i : sal) {
            al.add(i);
        }
        assertThat(al.toArray(), is(expected));
    }
}