package ru.job4j.map;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class MyHashMapTest {
    MyHashMap<String, String> mhm = new MyHashMap<>();

    @Test
    public void whenGet() {

        mhm.insert("0", "00");
        assertThat(mhm.get("0"), is("00"));
        mhm.insert("1", "11");
        assertThat(mhm.get("1"), is("11"));
        mhm.insert("2", "22");
        assertThat(mhm.get("2"), is("22"));
        mhm.insert("3", "33");
        assertThat(mhm.get("3"), is("33"));
        assertThat(mhm.get("0"), is("00"));
        assertThat(mhm.get("1"), is("11"));
        assertThat(mhm.get("2"), is("22"));
    }

    @Test
    public void whenInsert() {
        assertThat(mhm.insert("0", "00"), is(true));
        assertThat(mhm.insert("0", "123"), is(false));
    }


    @Test
    public void whenDelete() {
        mhm.insert("0", "00");
        mhm.insert("1", "11");
        assertThat(mhm.get("0"), is("00"));
        assertThat(mhm.get("1"), is("11"));
        boolean isDelete = mhm.delete("1");
        assertThat(isDelete, is(true));
        assertThat(mhm.get("1"), nullValue(null));
        isDelete = mhm.delete("1");
        assertThat(isDelete, is(false));
    }

    @Test
    public void whenIteratorTest() {
        Map.Entry item;
        List<String> keys = Arrays.asList("0", "1", "2");
        List<String> values = Arrays.asList("00", "11", "22");
        mhm.insert("0", "00");
        mhm.insert("1", "11");
        mhm.insert("2", "22");
        Iterator<Map.Entry> i = mhm.iterator();
        assertThat(i.hasNext(), is(true));
        assertThat(i.hasNext(), is(true));
        item = i.next();
        assertThat(i.hasNext(), is(true));
        assertThat(keys.contains(item.getKey()), is(true));
        assertThat(values.contains(item.getValue()), is(true));
        item = i.next();
        assertThat(i.hasNext(), is(true));
        assertThat(keys.contains(item.getKey()), is(true));
        assertThat(values.contains(item.getValue()), is(true));
        item = i.next();
        assertThat(keys.contains(item.getKey()), is(true));
        assertThat(values.contains(item.getValue()), is(true));
        assertThat(i.hasNext(), is(false));
        assertThat(i.hasNext(), is(false));
        assertThat(i.hasNext(), is(false));
    }

    @Test (expected = NoSuchElementException.class)
    public void whenHasNotNextItemThenException() {
        mhm.insert("0", "00");
        Iterator<Map.Entry> i = mhm.iterator();
        i.next();
        i.next();
    }

    @Test
    public void whenManyItemsThenCapacityChange() {
        int oldCap = mhm.getCapacity();
        for (int i = 0; i < 30; i++) {
            String str = ((Integer) i).toString();
            mhm.insert(str, str + str);
        }
        int newCap = mhm.getCapacity();
        assertThat(newCap > oldCap, is(true));
    }
}