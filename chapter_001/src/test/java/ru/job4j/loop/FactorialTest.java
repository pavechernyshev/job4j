package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class FactorialTest {

    @Test
    public void whenCalculateFactorialForFiveThenOneHundreedTwenty() {
       Factorial factorial = new Factorial();
       int res = factorial.calc(5);
       assertThat(res, is(120));
    }

    @Test
    public void whenCalculateFactorialForZeroThenOne() {
       Factorial factorial = new Factorial();
       int res = factorial.calc(0);
       assertThat(res, is(1));
    }
}
