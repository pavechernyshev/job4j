package ru.job4j.inout;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {
    public static void main(String args[]) {
        System.out.println(args);
        List<String> exts = new LinkedList<>();
        exts.add("txt");
        exts.add("java");
        new Zip().archive("\\projects\\job4j\\chapter_007\\src", exts);
    }

    public void archive(String startDirPath, List<String> exts) {
        Search search = new Search();
        List<File> filesList = search.files(startDirPath, exts);
        System.out.println(filesList);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("C:\\projects\\job4j\\chapter_007\\src\\main\\java\\ru\\job4j\\inout\\project.zip"))) {
            for (File file: filesList) {
                String relativeFilePath = file.getPath().replace(startDirPath, "").substring(1);
                zipOutputStream.putNextEntry(new ZipEntry(relativeFilePath));
                FileInputStream fileInputStream = new FileInputStream(file);
                write(fileInputStream, zipOutputStream);
                zipOutputStream.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[in.available()];
        while (in.read(buffer) != -1) {
            out.write(buffer);
        }
    }
}
