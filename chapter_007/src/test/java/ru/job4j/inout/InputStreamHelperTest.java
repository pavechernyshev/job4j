package ru.job4j.inout;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.io.*;
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

    @Test
    public void whenDropAbusesDropNothing() {
        String[] abuses = new String[0];
        String stringWithoutAbuses = "It is sting without abuses";
        String expectedString = stringWithoutAbuses;
        InputStream inputStreamWithAbuses = new ByteArrayInputStream(stringWithoutAbuses.getBytes(Charset.forName("UTF-8")));
        OutputStream result = new ByteArrayOutputStream(expectedString.length());
        OutputStream expected = new ByteArrayOutputStream(expectedString.length());
        try {
            expected.write(expectedString.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputStreamHelper.dropAbuses(inputStreamWithAbuses, result, abuses);
        assertThat(((ByteArrayOutputStream) result).toString(), Is.is(((ByteArrayOutputStream) expected).toString()));
    }

    @Test
    public void whenDropAbusesDropHelloAndBye() {
        String[] abuses = new String[2];
        abuses[0] = "Hello";
        abuses[1] = "bye";
        String stringWithAbuses = "Hello it is sting with abuses bye";
        String expectedString = "it is sting with abuses ";
        InputStream inputStreamWithAbuses = new ByteArrayInputStream(stringWithAbuses.getBytes(Charset.forName("UTF-8")));
        OutputStream result = new ByteArrayOutputStream(expectedString.length());
        OutputStream expected = new ByteArrayOutputStream(expectedString.length());
        try {
            expected.write(expectedString.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputStreamHelper.dropAbuses(inputStreamWithAbuses, result, abuses);
        assertThat(((ByteArrayOutputStream) result).toString(), Is.is(((ByteArrayOutputStream) expected).toString()));
    }
}