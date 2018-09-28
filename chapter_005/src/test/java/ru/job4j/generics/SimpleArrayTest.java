package ru.job4j.generics;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertTrue;

public class SimpleArrayTest {

    SimpleArray<Integer> sa = new SimpleArray<>(4);

    @Before
    public void fill() {
        sa.add(2);
        sa.add(4);
        sa.add(6);
        sa.add(8);
    }

    @Test
    public void get() {
        assertThat(sa.get(0), is(2));
        assertThat(sa.get(1), is(4));
        assertThat(sa.get(2), is(6));
        assertThat(sa.get(3), is(8));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void addOutOfSize() {
        sa.add(10);
    }

    @Test
    public void set() {
        sa.set(1, 9);
        sa.set(2, 0);
        assertThat(sa.get(0), is(2));
        assertThat(sa.get(1), is(9));
        assertThat(sa.get(2), is(0));
        assertThat(sa.get(3), is(8));
    }

    @Test
    public void delete() {
        sa.delete(0);
        sa.delete(3);
        assertThat(sa.get(0), is(nullValue()));
        assertThat(sa.get(1), is(4));
        assertThat(sa.get(2), is(6));
        assertThat(sa.get(3), is(nullValue()));
    }


    @Test
    public void iterator() {
        int counter = 0;
        for (Integer i: sa) {
            counter++;
        }
        assertThat(sa.getSize(), is(counter));
    }

    @Test
    public void whenIteratorHasNextThenTrue() throws Exception {
        Iterator<Integer> iterator = sa.iterator();
        assertTrue(iterator.hasNext());

    }

    @Test
    public void name() throws Exception {
        Iterator<Integer> iterator = sa.iterator();
        assertThat(iterator.next(), is(2));
    }
}