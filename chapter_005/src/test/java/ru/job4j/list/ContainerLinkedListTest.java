package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ContainerLinkedListTest {

    private ContainerLinkedList<Integer> list;

    @Before
    public void beforeTest() {
        list = new ContainerLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test
    public void whenAddThreeElementsThenUseGetOneResultTwo() {
        assertThat(list.get(1), is(2));
        assertThat(list.get(0), is(1));
        assertThat(list.get(2), is(3));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void whenAddThreeElementAndGetElementByIndexThreeThenException() {
        list.get(3);
    }

    @Test
    public void whenAddThreeElementsThenUseGetSizeResultThree() {
        assertThat(list.getSize(), is(3));
    }

    @Test
    public void whenAddTreeElementsThenTestIterable() {
        int i = 1;
        for (Integer item: list) {
            assertThat(item, is(i++));
        }
    }

    @Test
    public void whenAddTreeElementsThenTestIterator() {
        Iterator<Integer> it = list.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(false));
    }

    @Test (expected = NoSuchElementException.class)
    public void whenAddTreeElementsAndUseNextFourTimesThenException() {
        Iterator<Integer> it = list.iterator();
        it.next();
        it.next();
        it.next();
        it.next();
    }

    @Test (expected = NoSuchElementException.class)
    public void whenEmptyAndNextThenException() {
        Iterator<Integer> it = new ContainerLinkedList<Integer>().iterator();
        assertThat(it.hasNext(), is(false));
        it.next();
    }
}