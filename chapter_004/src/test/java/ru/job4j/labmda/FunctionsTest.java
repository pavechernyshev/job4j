package ru.job4j.labmda;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FunctionsTest {

    Functions functions = new Functions();

    @Test
    public void whenLineFunction() {
        List<Double> res = this.functions.diapason(0, 2, (n) -> 1 * n + 0);
        List<Double> expect = Arrays.asList(0d, 1d, 2d);
        assertThat(res, is(expect));
    }

    @Test
    public void whenSquareFunction() {
        List<Double> res = this.functions.diapason(0, 2, (n) -> 2 * Math.pow(n, 2) - 4 * n - 1);
        List<Double> expect = Arrays.asList(-1d, -3d, -1d);
        assertThat(res, is(expect));
    }

    /***
     * log3
     */
    @Test
    public void whenLogFunction() {
        List<Double> res = this.functions.diapason(1, 3, (n) -> Math.pow(n, 3));
        List<Double> expect = Arrays.asList(1d, 8d, 27d);
        assertThat(res, is(expect));
    }


}
