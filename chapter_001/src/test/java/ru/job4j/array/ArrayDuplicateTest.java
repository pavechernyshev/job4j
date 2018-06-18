package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class ArrayDuplicateTest {
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        String[] array = {"first", "second", "first", "third", "third", "fourth"};
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[] result = arrayDuplicate.remove(array);
        String[] expected = {"first", "second", "third", "fourth"};
        assertThat(result, is(expected));
    }

    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate123() {
        String[] array = {"1", "1", "3", "2", "2", "1"};
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[] result = arrayDuplicate.remove(array);
        String[] expected = {"1", "3", "2"};
        assertThat(result, is(expected));
    }

    @Test
    public void whenArrayWithoutDuplicatesThenSuchArray() {
        String[] array = {"1", "4", "3", "2"};
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[] result = arrayDuplicate.remove(array);
        String[] expected = {"1", "4", "3", "2"};
        assertThat(result, is(expected));
    }

    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate1236549870() {
        String[] array = {"1", "1", "2", "1", "3", "3", "1", "6", "6", "5", "3", "4", "5", "9", "1", "8", "7", "9", "8", "0"};
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[] result = arrayDuplicate.remove(array);
        String[] expected = {"1", "2", "3", "6", "5", "4", "9", "8", "7", "0"};
        assertThat(result, is(expected));
    }
}
