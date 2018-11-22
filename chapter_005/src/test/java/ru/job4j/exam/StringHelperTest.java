package ru.job4j.exam;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
public class StringHelperTest {
    @Test
    public void whenTwoWordsDifferentLengthThenFalse() {
        StringHelper sh = new StringHelper();
        boolean result = sh.equalOnChars("mama", "mamaa");
        assertFalse(result);
    }

    @Test
    public void whenTwoWordsEqualsCharsAndEqualsLengthAndDifferentRegisterThenFalse() {
        StringHelper sh = new StringHelper();
        boolean result = sh.equalOnChars("mAma", "mama");
        assertFalse(result);
    }

    @Test
    public void whenTwoWordsEqualsCharsAndEqualsLengthThenTrue() {
        StringHelper sh = new StringHelper();
        assertTrue(sh.equalOnChars("maam", "mama"));
        assertTrue(sh.equalOnChars("sssaaa", "aaasss"));
        assertTrue(sh.equalOnChars("ezrz", "zerz"));
        assertTrue(sh.equalOnChars("asd", "asd"));
        assertTrue(sh.equalOnChars("asd", "dsa"));
    }
}