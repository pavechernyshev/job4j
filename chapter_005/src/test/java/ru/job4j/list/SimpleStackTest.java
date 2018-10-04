package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
public class SimpleStackTest {

    private SimpleStack<Integer> ss = new SimpleStack<>();

    @Before
    public void fill() {
        ss.push(1);
        ss.push(2);
        ss.push(3);
    }

    @Test
    public void whenAddThreeItemsAndPollThenSizeEqualsTwo() {
        assertThat(ss.poll(), is(3));
        assertThat(ss.getSize(), is(2));
    }

    @Test
    public void whenAddThreeItemsThenSizeEqualsTree() {
        assertThat(ss.getSize(), is(3));
    }

    @Test
    public void whenAddThreeItemsAndPollThreeTimesThenSizeZero() {
        assertThat(ss.poll(), is(3));
        assertThat(ss.poll(), is(2));
        assertThat(ss.poll(), is(1));
        assertThat(ss.getSize(), is(0));
    }

    @Test (expected = NoSuchElementException.class)
    public void whenAddThreeItemsAndPollFourTimesException() {
        ss.poll();
        ss.poll();
        ss.poll();
        ss.poll();
    }

}