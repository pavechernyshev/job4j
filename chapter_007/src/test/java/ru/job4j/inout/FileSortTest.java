package ru.job4j.inout;

import com.google.common.base.Joiner;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class FileSortTest {
    private String fs = File.separator;
    private String ln = System.lineSeparator();

    String distFilePath = Joiner.on(fs).join(System.getProperty("user.dir"), "src", "test", "java", "ru", "job4j", "inout", "dist.txt");

    @Before
    public void deleteDistFail() {
        File dist = new File(distFilePath);
        dist.delete();
    }

    @Test
    public void asc() {
        File source = new File(Joiner.on(fs).join(System.getProperty("user.dir"),  "src", "test", "java", "ru", "job4j", "inout", "source.txt"));
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

    @Test
    public void smallFileSort() {
        File sourceFile = new File(Joiner.on(fs).join(System.getProperty("user.dir"),  "src", "test", "java", "ru", "job4j", "inout", "smallFileForSort.txt"));
        File distFile = new File(Joiner.on(fs).join(System.getProperty("user.dir"),  "src", "test", "java", "ru", "job4j", "inout", "smallFileSorted.txt"));
        List<String> lines = new LinkedList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(sourceFile))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
            lines.sort(Comparator.comparingInt(String::length));
            FileOutputStream fileOutputStream = new FileOutputStream(distFile);
            for (String line: lines) {
                String lineWithSeparator = String.format("%s%s", line, ln);
                fileOutputStream.write(lineWithSeparator.getBytes(Charset.forName("UTF-8")));
            }
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // проверка на корректность
        try (Scanner scanner = new Scanner(new FileInputStream(distFile))) {
            assertThat("22", Is.is(scanner.nextLine()));
            assertThat("333", Is.is(scanner.nextLine()));
            assertThat("333", Is.is(scanner.nextLine()));
            assertThat("4444", Is.is(scanner.nextLine()));
            assertThat("55555", Is.is(scanner.nextLine()));
            assertThat("55555", Is.is(scanner.nextLine()));
            assertThat("666666", Is.is(scanner.nextLine()));
            assertThat("666666", Is.is(scanner.nextLine()));
            assertThat("7777777", Is.is(scanner.nextLine()));
            assertThat("88888888", Is.is(scanner.nextLine()));
            assertThat("999999999", Is.is(scanner.nextLine()));
            assertThat("0000000000", Is.is(scanner.nextLine()));
            assertFalse(scanner.hasNextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertTrue(distFile.delete());
    }

}