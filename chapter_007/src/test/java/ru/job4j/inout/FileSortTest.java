package ru.job4j.inout;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

public class FileSortTest {
    String distFilePath = "src/test/java/ru/job4j/inout/dist.txt";

    @Before
    public void deleteDistFail() {
        File dist = new File(distFilePath);
        dist.delete();
    }

    @Ignore
    @Test
    public void asc() {
        File source = new File("src/test/java/ru/job4j/inout/source.txt");
        File dist = new File(distFilePath);
        FileSort fileSort = new FileSort();
        fileSort.asc(source, dist, 50);
        try (Scanner scanner = new Scanner(new FileInputStream(dist))) {
            assertTrue(scanner.hasNextLine());
            assertThat("1", Is.is(scanner.nextLine()));
            assertTrue(scanner.hasNextLine());
            assertThat("22", Is.is(scanner.nextLine()));
            assertTrue(scanner.hasNextLine());
            assertThat("22", Is.is(scanner.nextLine()));
            assertTrue(scanner.hasNextLine());
            assertThat("333", Is.is(scanner.nextLine()));
            assertTrue(scanner.hasNextLine());
            assertThat("333", Is.is(scanner.nextLine()));
            assertThat("4444", Is.is(scanner.nextLine()));
            assertThat("4444", Is.is(scanner.nextLine()));
            assertThat("55555", Is.is(scanner.nextLine()));
            assertThat("55555", Is.is(scanner.nextLine()));
            assertThat("55555", Is.is(scanner.nextLine()));
            assertThat("666666", Is.is(scanner.nextLine()));
            assertThat("666666", Is.is(scanner.nextLine()));
            assertThat("666666", Is.is(scanner.nextLine()));
            assertThat("7777777", Is.is(scanner.nextLine()));
            assertThat("7777777", Is.is(scanner.nextLine()));
            assertThat("7777777", Is.is(scanner.nextLine()));
            assertThat("88888888", Is.is(scanner.nextLine()));
            assertThat("88888888", Is.is(scanner.nextLine()));
            assertThat("88888888", Is.is(scanner.nextLine()));
            assertThat("999999999", Is.is(scanner.nextLine()));
            assertThat("0000000000", Is.is(scanner.nextLine()));
            assertFalse(scanner.hasNextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertTrue(dist.delete());
        try {
            assertTrue(dist.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}