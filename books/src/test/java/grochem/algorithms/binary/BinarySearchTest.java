package grochem.algorithms.binary;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinarySearchTest {
    @Test
    public void when123456AndSearch4ThenIndex3() {
        BinarySearch bs = new BinarySearch();
        int pos = bs.search(new int[]{1, 2, 3, 4, 5, 6}, 4);
        int operations = bs.getCountOperations();
        assertThat(pos, Is.is(3));
        assertThat(operations, Is.is(3));
    }

    @Test
    public void when12345AndSearch43ThenIndex2AndOperations1() {
        BinarySearch bs = new BinarySearch();
        int pos = bs.search(new int[]{1, 2, 3, 4, 5}, 3);
        int operations = bs.getCountOperations();
        assertThat(pos, Is.is(2));
        assertThat(operations, Is.is(1));
    }
}