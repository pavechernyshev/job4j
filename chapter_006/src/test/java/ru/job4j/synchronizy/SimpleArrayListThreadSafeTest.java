package ru.job4j.synchronizy;

import org.hamcrest.core.Is;
import org.hamcrest.core.Is.*;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.list.SimpleArrayList;


import java.util.Iterator;

import static org.junit.Assert.*;

public class SimpleArrayListThreadSafeTest {

    @Test
    public void whenTestIterator() {
        SimpleArrayList<Integer> sal = new SimpleArrayList<>();
        sal.add(1);
        sal.add(2);
        sal.add(3);
        SimpleArrayListThreadSafe<Integer> salts = new SimpleArrayListThreadSafe<>(sal);
        Iterator i = salts.iterator();
        salts.add(4);
        assertTrue(i.hasNext());
        assertThat(i.next(), Is.is(1));
        assertTrue(i.hasNext());
        assertThat(i.next(), Is.is(2));
        assertTrue(i.hasNext());
        assertThat(i.next(), Is.is(3));
        assertFalse(i.hasNext());
    }

}