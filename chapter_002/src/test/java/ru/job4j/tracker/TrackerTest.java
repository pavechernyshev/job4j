package ru.job4j.tracker;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TrackerTest {
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.findAll()[0], is(item));
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        // Добавляем заявку в трекер. Теперь в объект проинициализирован id.
        tracker.add(previous);
        // Создаем новую заявку.
        Item next = new Item("test2", "testDescription2", 1234L);
        // Проставляем старый id из previous, который был сгенерирован выше.
        next.setId(previous.getId());
        // Обновляем заявку в трекере.
        tracker.replace(previous.getId(), next);
        // Проверяем, что заявка с таким id имеет новые имя test2.
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }

    @Test
    public void whenAddNewItem() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        tracker.add(previous);
        assertThat(tracker.findById(previous.getId()).getName(), is("test1"));
    }

    @Test
    public void whenFindAll() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        Item second = new Item("test2", "testDescription2", 124L);
        Item third = new Item("test3", "testDescription3", 125L);
        tracker.add(previous);
        tracker.add(second);
        tracker.add(third);
        Item[] expected = {previous, second, third};
        Item[] res = tracker.findAll();
        assertThat(expected, is(res));
    }

    @Test
    public void whenDeleted() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        Item second = new Item("test2", "testDescription2", 124L);
        Item third = new Item("test3", "testDescription3", 125L);
        tracker.add(previous);
        tracker.add(second);
        tracker.add(third);
        tracker.delete(third.getId());
        Item[] expected = {previous, second};
        Item[] res = tracker.findAll();
        assertThat(expected, is(res));
    }

    @Test
    public void whenFindByName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        Item second = new Item("test2", "testDescription2", 124L);
        Item third = new Item("test3", "testDescription3", 125L);
        tracker.add(previous);
        tracker.add(second);
        tracker.add(third);
        Item[] expected = {second};
        Item[] res = tracker.findByName("test2");
        assertThat(expected, is(res));
    }

    @Test
    public void whenFindByNameTwoItems() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        Item second = new Item("test", "testDescription2", 124L);
        Item third = new Item("test", "testDescription3", 125L);
        tracker.add(previous);
        tracker.add(second);
        tracker.add(third);
        Item[] expected = {second, third};
        Item[] res = tracker.findByName("test");
        assertThat(expected, is(res));
    }

}
