package ru.job4j.inout;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class InputStreamHelperTest {

    InputStreamHelper inputStreamHelper = new InputStreamHelper();

    @Test
    public void whenNotNumThenFalse() {
        String s = "3";
        InputStream inputStreamS = new ByteArrayInputStream(s.getBytes(Charset.forName("UTF-8")));
        try {
            assertFalse(inputStreamHelper.isHasEvenNumber(inputStreamS));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenIsEvenNumThenTrue() {
        String s = "2";
        InputStream inputStreamS = new ByteArrayInputStream(s.getBytes(Charset.forName("UTF-8")));
        try {
            assertTrue(inputStreamHelper.isHasEvenNumber(inputStreamS));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}