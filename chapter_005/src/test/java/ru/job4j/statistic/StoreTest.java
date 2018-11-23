package ru.job4j.statistic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StoreTest {

    Store store = new Store();
    List<Store.User> previous = new ArrayList<>();
    List<Store.User> current = new ArrayList<>();

    @Before
    public void fill() {
        previous.add(new Store.User(1, "Pavel"));
        previous.add(new Store.User(2, "Vitaliy"));
        previous.add(new Store.User(3, "Ivan"));
        current.add(new Store.User(1, "Pavel"));
        current.add(new Store.User(2, "Vitaliy"));
        current.add(new Store.User(3, "Ivan"));
    }

    @Test
    public void whenDeleteOneUser() {
        Info expect = new Info(0, 0, 1);
        current.remove(2);
        Info res = store.diff(previous, current);
        assertEquals(expect, res);
    }
    @Test
    public void whenAddTwoUsers() {
        Info expect = new Info(2, 0, 0);
        current.add(new Store.User(4, "Andrey"));
        current.add(new Store.User(5, "Dmitri"));
        Info res = store.diff(previous, current);
        assertEquals(expect, res);
    }

    @Test
    public void whenChangeOneUser() {
        Info expect = new Info(0, 1, 0);
        current.get(1).name = "Vitali";
        Info res = store.diff(previous, current);
        assertEquals(expect, res);
    }


    @Test
    public void whenChangeOneUserAddTwoUsersDeleteOneUser() {
        Info expect = new Info(2, 1, 1);
        current.get(1).name = "Vitali";
        current.add(new Store.User(4, "Andrey"));
        current.add(new Store.User(5, "Dmitri"));
        current.remove(2);
        Info res = store.diff(previous, current);
        assertEquals(expect, res);
    }

    @Test
    public void whtNotChanges() {
        Info expect = new Info(0, 0, 0);
        Info res = store.diff(previous, current);
        assertEquals(expect, res);
    }



}