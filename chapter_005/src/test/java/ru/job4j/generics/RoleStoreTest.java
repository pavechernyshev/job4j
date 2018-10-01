package ru.job4j.generics;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RoleStoreTest {

    private RoleStore rs = new RoleStore(3);

    @Before
    public void fill() {
        this.rs.add(new Role("admin"));
        this.rs.add(new Role("root"));
        this.rs.add(new Role("content"));
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void add() {
        this.rs.add(new Role("Not"));
    }

    @Test
    public void replace() {
        Role sup = new Role("super");
        this.rs.replace("root", sup);
        assertThat(sup, is(rs.findById("super")));
        assertThat(null, is(rs.findById("root")));
    }

    @Test
    public void delete() {
        this.rs.delete("admin");
        this.rs.delete("root");
        this.rs.delete("content");
        assertThat(null, is(rs.findById("admin")));
        assertThat(null, is(rs.findById("root")));
        assertThat(null, is(rs.findById("content")));

    }

    @Test
    public void findById() {
        assertThat(new Role("admin"), is(rs.findById("admin")));
    }
}