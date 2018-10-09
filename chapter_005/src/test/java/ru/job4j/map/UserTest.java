package ru.job4j.map;

import org.junit.Before;
import org.junit.Test;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class UserTest {
    private User first;
    private User second;
    private User third;

    @Before
    public void fill() {
        first = new User("Pavel", new GregorianCalendar(1995, 6, 10));
        second = new User("Pavel", new GregorianCalendar(1995, 6, 10));
        third = new User("Ilya", new GregorianCalendar(1997, 6, 18));
    }

    @Test
    public void whenFirstEqualsSecondThenTrue() {
        assertThat(first.equals(second), is(true));
    }

    @Test
    public void whenFirstEqualsThirdThenFalse() {
        assertThat(first.equals(third), is(false));
    }

    @Test
    public void whenDifferentCountChildrenThenFalse() {
        first.setChildren(1);
        assertThat(first.equals(second), is(false));
    }
}