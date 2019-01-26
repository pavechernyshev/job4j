package ru.job4j.inout;

import javafx.collections.transformation.SortedList;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class FileSort {
    List<String> linesBufferList = new LinkedList<>();
    private int maxCanReadRowsCount = 2; //максимальное количество строк, которое мы можем позволить себе хранить в памяти
    private String pathToTmpDir = "src/main/java/ru/job4j/inout/tmp";
    private int tmpFilesCount = 0;

    public void asc(File source, File dist) {
        try (Scanner scanner = new Scanner(new FileInputStream(source))) {
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                linesBufferList.add(currentLine);
                boolean linesBufferIsFull = linesBufferList.size() >= maxCanReadRowsCount;
                boolean endOfSourceFile = !scanner.hasNextLine();
                if (linesBufferIsFull || endOfSourceFile) {
                    sortLinesBuffer();
                    printLinesBufferToTmpFile();
                    linesBufferList.clear();
                }
            }
            mergeTmpFiles(dist);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sortLinesBuffer() {
        this.linesBufferList.sort(Comparator.comparingInt(String::length));
    }

    private void printLinesBufferToTmpFile() {
        StringBuilder linesStringBuilder = new StringBuilder();
        String lineSeparator = "\n";
        for (String aLinesBufferList : linesBufferList) {
            linesStringBuilder.append(aLinesBufferList).append(lineSeparator);
        }
        String tmpFileName = String.format("%s/tmpFile%s.txt", pathToTmpDir, ++this.tmpFilesCount);
        File tmpFile = new File(tmpFileName);
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(tmpFile, "rw");
            randomAccessFile.write(linesStringBuilder.toString().getBytes(Charset.forName("UTF-8")));
            randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Получаем список файлов с отсортированными строками
     * сортируем список файлов по длине первой строки
     * пишем в целевой файл
     * очищайем все временные файлы
     */
    private void mergeTmpFiles(File dist) {
        File[] tmpFolderFiles = new File(pathToTmpDir).listFiles();
        boolean filesExist = tmpFolderFiles != null && tmpFolderFiles.length > 0;
        if (filesExist) {
            List<File> filesList = new LinkedList<>(Arrays.asList(tmpFolderFiles));
            filesList.sort(Comparator.comparingInt(FileSort::getFileFirstLineLength));
            long lastFilePointerPos = 0;
            for (File fileWithMinFirstLine: filesList) {
                try {
                    RandomAccessFile randomAccessDistFile = new RandomAccessFile(dist.getPath(), "rw");
                    Scanner cutFileScanner = new Scanner(new FileInputStream(fileWithMinFirstLine));
                    randomAccessDistFile.seek(lastFilePointerPos);
                    while (cutFileScanner.hasNextLine()) {
                        String writeLine = String.format("%s\n", cutFileScanner.nextLine());
                        randomAccessDistFile.write(writeLine.getBytes(Charset.forName("UTF-8")));
                    }
                    lastFilePointerPos = randomAccessDistFile.getFilePointer();
                    randomAccessDistFile.close();
                    cutFileScanner.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
            clearTmpDir();
        }
    }

    private void clearTmpDir() {
        File[] tmpFolderFiles = new File(pathToTmpDir).listFiles();
        boolean filesExist = tmpFolderFiles != null && tmpFolderFiles.length > 0;
        if (filesExist) {
            for (File file : tmpFolderFiles) {
                boolean fileWasDelete = file.delete();
                if (!fileWasDelete) {
                    new IOException(String.format("Не удалось удалить файл: %s", file.getPath())).printStackTrace();
                }
            }
        }
    }

    private static int getFileFirstLineLength(File file) {
        int res = 0;
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            if (scanner.hasNextLine()) {
                String firstLine = scanner.nextLine();
                res = firstLine.length();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }
}
