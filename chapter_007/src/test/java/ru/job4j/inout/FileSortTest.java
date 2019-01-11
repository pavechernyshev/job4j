package ru.job4j.inout;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

public class FileSortTest {
    String distFilePath = "src/test/java/ru/job4j/inout/dist.txt";

    @Test
    public void asc() {
        File source = new File("src/test/java/ru/job4j/inout/source.txt");
        File dist = new File(distFilePath);
        FileSort fileSort = new FileSort();
        fileSort.asc(source, dist);
        try (Scanner scanner = new Scanner(new FileInputStream(dist))) {
            assertTrue(scanner.hasNextLine());
            assertThat("один", Is.is(scanner.nextLine()));
            assertTrue(scanner.hasNextLine());
            assertThat("два два", Is.is(scanner.nextLine()));
            assertTrue(scanner.hasNextLine());
            assertThat("три три три", Is.is(scanner.nextLine()));
            assertTrue(scanner.hasNextLine());
            assertThat("четыре четыре четыре", Is.is(scanner.nextLine()));
            assertTrue(scanner.hasNextLine());
            assertThat("пять пять пять пять пять", Is.is(scanner.nextLine()));
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