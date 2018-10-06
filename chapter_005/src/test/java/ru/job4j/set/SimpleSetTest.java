package ru.job4j.set;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleSetTest {
    SimpleSet<Integer> ss = new SimpleSet<>();

    @Before
    public void fill() {
        ss.add(1);
        ss.add(1);
        ss.add(2);
        ss.add(2);
        ss.add(3);
        ss.add(1);
        ss.add(3);
    }

    @Test
    public void whenAdd1122313Then123() {
        int[] array = new int[3];
        int[] expect = {1, 2, 3};
        int counter = 0;
        for (Integer i: ss) {
            array[counter++] = i;
        }
        assertThat(array, Is.is(expect));
    }
}