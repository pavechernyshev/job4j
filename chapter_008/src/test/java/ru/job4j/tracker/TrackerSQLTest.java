package ru.job4j.tracker;

import org.junit.After;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class TrackerSQLTest {

    public TrackerSQLTest() throws SQLException {
    }

    public Connection init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void createItem() throws SQLException {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name", "desc"));
            assertThat(tracker.findByName("name").size(), is(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    TrackerSQL sql = new TrackerSQL(ConnectionRollback.create(this.init()));

    @After
    public void after() throws Exception {
        sql.close();
    }

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
        assertThat(found.getDescription(), is(added.getDescription()));
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
        assertThat(found.getDescription(), is(added.getDescription()));
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