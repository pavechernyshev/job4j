package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
public class SimpleQueueTest {

    SimpleQueue<Integer> sq = new SimpleQueue<>();

    @Before
    public void fill() {
        sq.push(1);
        sq.push(2);
        sq.push(3);
    }

    @Test
    public void whenAddThreeItemsAndPollThreeTimesThen123() {
        assertThat(sq.poll(), is(1));
        assertThat(sq.poll(), is(2));
        assertThat(sq.poll(), is(3));
    }

    @Test
    public void whenAddThreeItemsAndPollTwoTimesAndAddTwoItemsAndPollThreeTimesThen12345() {
        assertThat(sq.poll(), is(1));
        assertThat(sq.poll(), is(2));
        sq.push(4);
        sq.push(5);
        assertThat(sq.poll(), is(3));
        assertThat(sq.poll(), is(4));
        assertThat(sq.poll(), is(5));
    }

    @Test (expected = NoSuchElementException.class)
    public void whenAddThreeItemsAndFourTimesPollThenException() {
        sq.poll();
        sq.poll();
        sq.poll();
        sq.poll();
    }

}