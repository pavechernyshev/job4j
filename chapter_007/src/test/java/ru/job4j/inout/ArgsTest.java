package ru.job4j.inout;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArgsTest {
    Args args;

    @Test
    public void getStartDirPath() {
        String[] stringArgs = {"-d", "c:\\project\\job4j\\", "-e", "java.xml", "-o", "project.zip"};
        args = new Args(stringArgs);
        args.init();
        assertThat(args.getStartDirPath(), Is.is("c:\\project\\job4j\\"));
        assertThat(args.getExcludeFilesList().size(), Is.is(1));
        assertThat(args.getExcludeFilesList().get(0), Is.is("java.xml"));
        assertThat(args.getOutputFileName(), Is.is("project.zip"));
    }

    @Test
    public void getExcludeFilesList() {
        String[] stringArgs = {"-d", "c:\\project\\job4j\\", "-e", "java.xml", "*.txt", "doc.doc", "-o", "project.zip"};
        args = new Args(stringArgs);
        args.init();
        assertThat(args.getStartDirPath(), Is.is("c:\\project\\job4j\\"));
        assertThat(args.getExcludeFilesList().size(), Is.is(3));
        assertThat(args.getExcludeFilesList().get(0), Is.is("java.xml"));
        assertThat(args.getExcludeFilesList().get(1), Is.is("*.txt"));
        assertThat(args.getExcludeFilesList().get(2), Is.is("doc.doc"));
        assertThat(args.getOutputFileName(), Is.is("project.zip"));
    }

    @Test
    public void getOutputFileName() {
        String[] stringArgs = {"-d", "c:\\project\\job4j\\", "-output", "project.zip", "-e", "java.xml", "*.txt", "doc.doc"};
        args = new Args(stringArgs);
        args.init();
        assertThat(args.getStartDirPath(), Is.is("c:\\project\\job4j\\"));
        assertThat(args.getExcludeFilesList().size(), Is.is(3));
        assertThat(args.getExcludeFilesList().get(0), Is.is("java.xml"));
        assertThat(args.getExcludeFilesList().get(1), Is.is("*.txt"));
        assertThat(args.getExcludeFilesList().get(2), Is.is("doc.doc"));
        assertThat(args.getOutputFileName(), Is.is("project.zip"));
    }
}