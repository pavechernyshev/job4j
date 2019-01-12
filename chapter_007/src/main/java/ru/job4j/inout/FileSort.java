package ru.job4j.inout;

import javafx.collections.transformation.SortedList;

import java.io.*;
import java.util.*;

public class FileSort {
    List<Integer> linesLenList = new LinkedList<>();

    public void asc(File source, File distance) {
        try (Scanner scanner = new Scanner(new FileInputStream(source))) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(distance, "rw");
            boolean isFirstWrite = true;
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                if (!isFirstWrite) {
                    int pastePointerPos = getPointerIndexToWrite(currentLine.length());
                    randomAccessFile.seek(pastePointerPos);
                    randomAccessFile.write(currentLine.getBytes("UTF-8"));
                } else {
                    randomAccessFile.write(currentLine.getBytes("UTF-8"));
                    isFirstWrite = false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getPointerIndexToWrite(int lineLen) {
        linesLenList.add(lineLen);
        Collections.sort(linesLenList);
        int res = 0;
        for (int i = 0; i < linesLenList.indexOf(lineLen); i++) {
            res += linesLenList.get(i);
        }
        return res;
    }
}
