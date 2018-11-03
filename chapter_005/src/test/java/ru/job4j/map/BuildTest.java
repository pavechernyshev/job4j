package ru.job4j.map;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.*;

public class BuildTest {

    @Test
    public void whenTwoObjectEqualsThenSameHashCode() {
        Build one = new Build("Континент", 2, 3.5f, true);
        Build two = new Build("Континент", 2, 3.5f, true);
        assertThat(one.hashCode() == two.hashCode(), Is.is(true));
        assertThat(one.equals(two), Is.is(true));
    }
    @Test

    public void whenTwoObjectDifferentThenSameHashCode() {
        Build one = new Build("Континент", 2, 3.5f, true);
        Build two = new Build("Континент", 3, 3.5f, true);
        assertThat(one.hashCode() == two.hashCode(), Is.is(false));
        assertThat(one.equals(two), Is.is(false));
    }

}