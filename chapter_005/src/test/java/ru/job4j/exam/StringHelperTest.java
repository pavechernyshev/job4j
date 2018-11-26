package ru.job4j.exam;

import org.junit.Test;
import static org.junit.Assert.*;
public class StringHelperTest {
    @Test
    public void whenTwoWordsDifferentLengthThenFalse() {
        StringHelper sh = new StringHelper();
        boolean result = sh.checkWordsForSameChars("mama", "mamaa");
        assertFalse(result);
    }

    @Test
    public void whenTwoWordsEqualsCharsAndEqualsLengthAndDifferentRegisterThenFalse() {
        StringHelper sh = new StringHelper();
        boolean result = sh.checkWordsForSameChars("mAma", "mama");
        assertFalse(result);
    }

    @Test
    public void whenTwoWordsEqualsCharsAndEqualsLengthThenTrue() {
        StringHelper sh = new StringHelper();
        assertTrue(sh.checkWordsForSameChars("maam", "mama"));
        assertTrue(sh.checkWordsForSameChars("sssaaa", "aaasss"));
        assertTrue(sh.checkWordsForSameChars("ezrz", "zerz"));
        assertTrue(sh.checkWordsForSameChars("asd", "asd"));
        assertTrue(sh.checkWordsForSameChars("asd", "dsa"));
    }

    @Test
    public void whenEqualsLettersButDifferentLettersCountThenFalse() {
        StringHelper sh = new StringHelper();
        assertFalse(sh.checkWordsForSameChars("maam", "maaa"));
        assertFalse(sh.checkWordsForSameChars("desee", "dsees"));
    }
}