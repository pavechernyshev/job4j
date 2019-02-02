package ru.job4j.inout;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class SearchTest {
    String rootPath = System.getProperty("java.io.tmpdir") + "\\mytmp\\";
    Search search = new Search();
    List<File> txtFilesList = new LinkedList<>();
    List<File> jpgFilesList = new LinkedList<>();
    List<File> docFilesList = new LinkedList<>();

    @Ignore
    @Before
    public void fillTmpdir() {
        File tmpdir = new File(rootPath);
        File ftxt = new File(rootPath + "f.txt");
        File fdoc = new File(rootPath + "f.doc");
        File subdoc = new File(rootPath + "sub/1.1.doc");
        File subdir = new File(rootPath + "sub");
        File subjpg = new File(rootPath + "sub/1.1.jpg");
        File subtxt = new File(rootPath + "sub/1.1.txt");
        File subsub1dir = new File(rootPath + "sub/sub1");
        File subsub2dir = new File(rootPath + "sub/sub2");
        File subsubtxt1 = new File(rootPath + "sub/sub1/1.1.txt");
        File subsubtxt12 = new File(rootPath + "sub/sub2/1.2.txt");
        File sub2dir = new File(rootPath + "sub2");
        File sub2txt = new File(rootPath + "sub2/f.txt");
        File sub2stxt = new File(rootPath + "sub2/s.txt");
        File sub3dir = new File(rootPath + "sub3");
        File sub3fdoc = new File(rootPath + "sub3/f.doc");
        File sub3sdoc = new File(rootPath + "sub3/s.doc");
        try {
            tmpdir.mkdir();
            subdir.mkdir();
            subsub1dir.mkdir();
            subsub2dir.mkdir();
            sub2dir.mkdir();
            sub3dir.mkdir();
            ftxt.createNewFile();
            fdoc.createNewFile();
            subdoc.createNewFile();
            subjpg.createNewFile();
            subtxt.createNewFile();
            subsubtxt1.createNewFile();
            subsubtxt12.createNewFile();
            sub2txt.createNewFile();
            sub2stxt.createNewFile();
            sub3fdoc.createNewFile();
            sub3sdoc.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        txtFilesList.add(ftxt);
        txtFilesList.add(subtxt);
        txtFilesList.add(subsubtxt1);
        txtFilesList.add(subsubtxt12);
        txtFilesList.add(sub2txt);
        txtFilesList.add(sub2stxt);
        docFilesList.add(fdoc);
        docFilesList.add(subdoc);
        docFilesList.add(sub3fdoc);
        docFilesList.add(sub3sdoc);
        jpgFilesList.add(subjpg);
    }

    @Ignore
    @Test
    public void searchTxtFiles() {
        List<String> exts = new LinkedList<>();
        exts.add("txt");
        List<File> foundFiles = search.files(rootPath, exts);
        for (File file: foundFiles)  {
            assertTrue(txtFilesList.contains(file));
            assertTrue(txtFilesList.remove(file));
        }
        assertThat(txtFilesList.size(), Is.is(0));
    }

    @Ignore
    @Test
    public void searchJpgFiles() {
        List<String> exts = new LinkedList<>();
        exts.add("jpg");
        List<File> foundFiles = search.files(rootPath, exts);
        for (File file: foundFiles)  {
            assertTrue(jpgFilesList.contains(file));
            assertTrue(jpgFilesList.remove(file));
        }
        assertThat(jpgFilesList.size(), Is.is(0));
    }

    @Ignore
    @Test
    public void searchDocFiles() {
        List<String> exts = new LinkedList<>();
        exts.add("doc");
        List<File> foundFiles = search.files(rootPath, exts);
        for (File file: foundFiles)  {
            assertTrue(docFilesList.contains(file));
            assertTrue(docFilesList.remove(file));
        }
        assertThat(docFilesList.size(), Is.is(0));
    }
}