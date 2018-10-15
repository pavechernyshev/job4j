package ru.job4j.map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void whenGetNameOnThirdThenIlya() {
        assertThat(third.getName(), is("Ilya"));
    }

    @Test
    public void whenGetFirstChildrenThen0() {
        assertThat(first.getChildren(), is(0));
    }

    @Test
    public void whenGetThirdBirthdayEqualsNewCalendar19970618ThenTrue() {
        assertThat(third.getBirthday(), is(new GregorianCalendar(1997, 6, 18)));
    }

    @Test
    @Ignore
    public void whenDoNotOverrideEqualsAndHashCodeAndCompareSuchObjectsThenTwoItems() {
        Map<User, Integer> map = new HashMap<>();
        map.put(first, 1);
        map.put(second, 2);
        assertThat(map.size(), is(2));
    }

    @Test
    @Ignore
    public void whenDoNotOverrideEqualsAndCompareSuchObjectsThenTwoItems() {
        Map<User, Integer> map = new HashMap<>();
        map.put(first, 3);
        map.put(second, 5);
        assertThat(map.size(), is(2));
    }

    @Test
    @Ignore
    public void whenDoNotOverrideHashCodeAndCompareSuchObjectsThenTwoItems() {
        Map<User, Integer> map = new HashMap<>();
        map.put(first, 6);
        map.put(second, 8);
        assertThat(map.size(), is(2));
    }
}