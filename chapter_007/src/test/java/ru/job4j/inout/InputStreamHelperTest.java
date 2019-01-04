package ru.job4j.inout;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class InputStreamHelperTest {

    InputStreamHelper inputStreamHelper = new InputStreamHelper();

    @Test
    public void whenNotNumThenFalse() {
        System.setIn(new InputStream() {
            @Override
            public int read() throws IOException {
                return 'a';
            }
        });
        assertFalse(inputStreamHelper.isNumber(System.in));
    }

    @Test
    public void whenIsNumThenTrue() {
        System.setIn(new InputStream() {
            @Override
            public int read() throws IOException {
                return '2';
            }
        });
        assertTrue(inputStreamHelper.isNumber(System.in));
    }

}