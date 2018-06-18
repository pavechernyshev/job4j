package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class ArraySortTest {
    @Test
    public void whenSortArrayOneMergeWithSortArrayTwoThenSortArrayThree() {
        int[] one = {1, 5, 8};
        int[] two = {2, 4, 6, 7, 9};
        int[] expect = {1, 2, 4, 5, 6, 7, 8, 9};
        ArraySort arraySort = new ArraySort();
        int[] res = arraySort.mergeAndSort(one, two);
        assertThat(res, is(expect));
    }

    @Test
    public void whenSortArrayOneMergeWithSortArrayTwoThenSortArrayThreeV2() {
        int[] one = {1, 3, 4};
        int[] two = {2, 4, 4, 7, 9};
        int[] expect = {1, 2, 3, 4, 4, 4, 7, 9};
        ArraySort arraySort = new ArraySort();
        int[] res = arraySort.mergeAndSort(one, two);
        assertThat(res, is(expect));
    }

    @Test
    public void whenSortArrayOneMergeWithSortArrayTwoThenSortArrayThreeV3() {
        int[] one = {1, 2, 3};
        int[] two = {4, 5, 6};
        int[] expect = {1, 2, 3, 4, 5, 6};
        ArraySort arraySort = new ArraySort();
        int[] res = arraySort.mergeAndSort(one, two);
        assertThat(res, is(expect));
    }
}
