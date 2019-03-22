package ru.job4j.exam;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FindTest {
    Find find = new Find();
    Find.FullMatch fullMatch = find.new FullMatch();
    Find.Mask mask = find.new Mask();
    Find.Reg reg = find.new Reg();


    @Test
    public void start() {
    }

    @Test
    public void whenEqualsFullMath() {
        fullMatch.setMask("test");
        assertTrue(fullMatch.isEqual("test"));
    }

    @Test
    public void whenNotEqualsFullMath() {
        fullMatch.setMask("test");
        assertFalse(fullMatch.isEqual("Test"));
    }

    @Test
    public void whenEqualsByMask() {
        mask.setMask("*.txt");
        assertTrue(mask.isEqual("test.txt"));
        assertTrue(mask.isEqual("ascds.txt"));
        assertTrue(mask.isEqual("s.txt"));
        mask.setMask("*.*");
        assertTrue(mask.isEqual("s.s"));
    }

    @Test
    public void whenNotEqualsByMask() {
        mask.setMask("*.txt");
        assertFalse(mask.isEqual("test.txT"));
        assertFalse(mask.isEqual("test.tt"));
        mask.setMask("*.*");
        assertFalse(mask.isEqual("ss"));
    }

    @Test
    public void whenFindByReg() {
        reg.setMask("[0-9]+");
        assertTrue(reg.isEqual("88775"));
        assertTrue(reg.isEqual("88789"));
    }
    @Test
    public void whenNotFindByReg() {
        reg.setMask("[0-9]+");
        assertFalse(reg.isEqual("8_"));
        assertFalse(reg.isEqual("88789O"));
    }


}