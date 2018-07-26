package ru.job4j.sort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SortUserTest {
    @Test
    public void whenUserListThenSortedUsers() {
        SortUser sortUser = new SortUser();
        List<User> users = Arrays.asList(
                new User("Pavel", 23),
                new User("Ilya", 21),
                new User("Lola", 22),
                //new User("Alena", 22),
                new User("Tom", 12)
        );
        Set<User> res = sortUser.sort(users);
        List<User> expect = Arrays.asList(
                new User("Tom", 12),
                new User("Ilya", 21),
                new User("Lola", 22),
                //new User("Alena", 22),
                new User("Pavel", 23)
        );
        assertThat(res.toString(), is(expect.toString()));
    }

    @Test
    public void whenSortUsersByNameLength() {
        SortUser sortUser = new SortUser();
        List<User> users = Arrays.asList(
                new User("Pavel", 23),
                new User("Ilya", 21),
                new User("Lola", 22),
                new User("Alena", 22),
                new User("Tom", 12)
        );
        List<User> res = sortUser.sortNameLength(users);
        List<User> expect = Arrays.asList(
                new User("Tom", 12),
                new User("Ilya", 21),
                new User("Lola", 22),
                new User("Pavel", 23),
                new User("Alena", 22)
        );
        assertThat(res.toString(), is(expect.toString()));
    }

    @Test
    public void whenSortUsersByAllFields() {
        SortUser sortUser = new SortUser();
        List<User> users = Arrays.asList(
                new User("Pavel", 23),
                new User("Ilya", 21),
                new User("Lola", 22),
                new User("Alena", 19),
                new User("Tom", 12)
        );
        List<User> res = sortUser.sortByAllFields(users);
        List<User> expect = Arrays.asList(
                new User("Tom", 12),
                new User("Ilya", 21),
                new User("Lola", 22),
                new User("Alena", 19),
                new User("Pavel", 23)
        );
        assertThat(res.toString(), is(expect.toString()));
    }
}
