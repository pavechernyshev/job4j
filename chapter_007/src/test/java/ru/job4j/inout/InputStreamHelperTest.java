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
        try {
            assertFalse(inputStreamHelper.isHasEvenNumber(System.in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenIsNumThenTrue() {
        System.setIn(new InputStream() {
            @Override
            public int read() throws IOException {
                return '2';
            }
        });
        try {
            assertTrue(inputStreamHelper.isHasEvenNumber(System.in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}