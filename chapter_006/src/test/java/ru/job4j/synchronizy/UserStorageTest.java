package ru.job4j.synchronizy;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

public class UserStorageTest {

    private UserStorage userStorage = new UserStorage();

    @Before
    public void init() {
        userStorage.add(new User(1, 200));
        userStorage.add(new User(2, 300));
        userStorage.add(new User(3, 150));
    }

    @Test
    public void whenAdd() {
        assertTrue(userStorage.add(new User(4, 200)));
        assertFalse(userStorage.add(new User(1, 200)));
    }

    @Test
    public void whenUpdate() {
        User newUser = new User(2, 400);
        assertTrue(userStorage.update(newUser));
        assertFalse(userStorage.update(new User(4, 1)));
    }

    @Test
    public void whenDelete() {
        assertTrue(userStorage.delete(new User(2, 0)));
        assertFalse(userStorage.delete(new User(2, 0)));
    }

    @Test
    public void whenTransfer() {
        User userFrom = new User(4, 500);
        User userTo = new User(5, 500);
        userStorage.add(userFrom);
        userStorage.add(userTo);
        userStorage.transfer(4, 5, 500);
        assertThat(userFrom.getAmount(), is(0));
        assertThat(userTo.getAmount(), is(1000));
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenTransferHasNotEnoughMoney() {
        userStorage.transfer(1, 2, 500);
    }
}