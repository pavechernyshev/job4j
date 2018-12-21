package ru.job4j.pool;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    User user = new User("Pavel", "test@yandex.ru");

    @Test
    public void getName() {
        assertThat(user.getName(), Is.is("Pavel"));
    }

    @Test
    public void getEmail() {
        assertThat(user.getEmail(), Is.is("test@yandex.ru"));
    }
}