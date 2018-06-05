package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class CounterTest {

   @Test
   public void whenSumEvenNumsFromOneToTen() {
       Counter counter = new Counter();
       int res = counter.add(1, 10);
       assertThat(res, is(30));
   }
}
