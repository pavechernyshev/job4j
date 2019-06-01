package ru.job4j.tracker;

import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class TrackerSQLTest {

    TrackerSQL sql = new TrackerSQL();

    @Test
    public void checkConnection() {
        assertThat(sql.init(), is(true));
    }

    @Test
    public void setAndGetOneItem() {
        sql.init();
        Item added = sql.add(new Item("Notebook FFi", "Acer 23"));
        Item found = sql.findById(added.getId());
        assertThat(found.getName(), is(added.getName()));
        assertThat(found.getDesc(), is(added.getDesc()));
        assertTrue(added.equals(found));
        assertTrue(sql.delete(added.getId()));
    }

    @Test
    public void update() {
        sql.init();
        Item added = sql.add(new Item("Notebook", "Acer"));
        added.setName("Laptok");
        sql.replace(added.getId(), added);
        Item found = sql.findById(added.getId());
        assertThat(found.getName(), is(added.getName()));
        assertThat(found.getDesc(), is(added.getDesc()));
        assertThat(found.getCreated(), is(added.getCreated()));
        assertTrue(added.equals(found));
        assertTrue(sql.delete(added.getId()));
    }

    @Test
    public void whenFindByNameTwoItems() {
        sql.init();
        Item previous = new Item("test1", "testDescription", 123L);
        Item second = new Item("test", "testDescription2", 124L);
        Item third = new Item("test", "testDescription3", 125L);
        sql.add(previous);
        sql.add(second);
        sql.add(third);
        List<Item> expected = Arrays.asList(second, third);
        List<Item> res = sql.findByName("test");
        assertThat(expected, is(res));
        sql.delete(previous.getId());
        sql.delete(second.getId());
        sql.delete(third.getId());
    }

    @Test
    public void whenFindAll() {
        sql.init();
        Item previous = new Item("test1", "testDescription", 123L);
        Item second = new Item("test2", "testDescription2", 124L);
        Item third = new Item("test3", "testDescription3", 125L);
        sql.add(previous);
        sql.add(second);
        sql.add(third);
        List<Item> expected = Arrays.asList(previous, second, third);
        List<Item> res = sql.findAll();
        assertThat(expected, is(res));
        sql.delete(previous.getId());
        sql.delete(second.getId());
        sql.delete(third.getId());
    }


}