package ru.job4j.generics;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserStoreTest {

    private UserStore us = new UserStore(3);

    @Before
    public void fill() {
        this.us.add(new User("Pavel"));
        this.us.add(new User("Andrey"));
        this.us.add(new User("Ilya"));
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void add() {
        this.us.add(new User("Out"));
    }

    @Test
    public void replace() {
        User olya = new User("Olya");
        this.us.replace("Andrey", olya);
        assertThat(olya, is(us.findById("Olya")));
        assertThat(null, is(us.findById("Andrey")));
    }

    @Test
    public void delete() {
        this.us.delete("Pavel");
        this.us.delete("Andrey");
        this.us.delete("Ilya");
        assertThat(null, is(us.findById("Pavel")));
        assertThat(null, is(us.findById("Andrey")));
        assertThat(null, is(us.findById("Ilya")));

    }

    @Test
    public void findById() {
        assertThat(new User("Pavel"), is(us.findById("Pavel")));
    }
}