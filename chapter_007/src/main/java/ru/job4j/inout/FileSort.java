package ru.job4j.inout;

import com.google.common.base.Joiner;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class FileSort {
    private String fs = File.separator;

    private String pathToSplitDir = Joiner.on(fs).join(System.getProperty("user.dir"), "src", "main", "java", "ru", "job4j", "inout", "split");
    private String ln = System.lineSeparator();

    public void asc(File source, File dist, int maxBiteSize) {
        if (!checkSplitDir()) {
            createSplitDir();
        }
        clearSplitDir();
        List<File> splitFilesList = splitFileIntoPieces(source, maxBiteSize);
        splitFilesList.sort(Comparator.comparingInt(FileSort::getFileFirstLineLength));
        sortFilesContent(splitFilesList);
        mergeFiles(splitFilesList, dist);
        clearSplitDir();
    }

    private boolean checkSplitDir() {
        File file = new File(pathToSplitDir);
        return file.exists() && file.isDirectory();
    }

    private boolean createSplitDir() {
        File file = new File(pathToSplitDir);
        return file.mkdir();
    }

    private void mergeFiles(List<File> filesList, File dist) {
        HashMap<File, Long> fileToLastPosMap = new HashMap<>();
        for (File file: filesList) {
            fileToLastPosMap.put(file, 0L);
        }
        try {
            RandomAccessFile distRandomAccessFile = new RandomAccessFile(dist, "rw");
            long lastFilePointerPos = 0;
            while (filesList.size() > 0) {
                File fileWithNextMinLine = null;
                long fileMinLineSize = -1;
                for (File file: filesList) {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                    long fileLastPos = fileToLastPosMap.get(file);
                    randomAccessFile.seek(fileLastPos);
                    String curLine = randomAccessFile.readLine();
                    boolean fileInit = fileWithNextMinLine != null && fileMinLineSize != -1;
                    if (!fileInit) {
                        fileWithNextMinLine = file;
                        fileMinLineSize = curLine.length();
                    } else if (curLine.length() < fileMinLineSize) {
                        fileWithNextMinLine = file;
                        fileMinLineSize = curLine.length();
                    }
                    randomAccessFile.close();
                }
                RandomAccessFile minLineRandomAccessFile = new RandomAccessFile(fileWithNextMinLine, "r");
                minLineRandomAccessFile.seek(fileToLastPosMap.get(fileWithNextMinLine));
                String writeLine = String.format("%s%s", minLineRandomAccessFile.readLine(), ln);
                long newFilePointerPos = minLineRandomAccessFile.getFilePointer();
                boolean fileFullyRead = minLineRandomAccessFile.readLine() == null;
                if (fileFullyRead) {
                    fileToLastPosMap.remove(fileWithNextMinLine);
                    filesList.remove(fileWithNextMinLine);
                } else {
                    fileToLastPosMap.replace(fileWithNextMinLine, newFilePointerPos);
                }
                distRandomAccessFile.seek(lastFilePointerPos);
                distRandomAccessFile.write(writeLine.getBytes(Charset.forName("UTF-8")));
                lastFilePointerPos = distRandomAccessFile.getFilePointer();
                minLineRandomAccessFile.close();
            }
            distRandomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sortFilesContent(List<File> files) {
        for (File file: files) {
            List<String> linesList = new LinkedList<>();
            try (Scanner scanner = new Scanner(new FileInputStream(file))) {
                while (scanner.hasNextLine()) {
                    linesList.add(scanner.nextLine());
                }
                scanner.close();
                linesList.sort(Comparator.comparingInt(String::length));
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                for (String line: linesList) {
                    fileOutputStream.write(String.format("%s%s", line, ln).getBytes(Charset.forName("UTF-8")));
                }
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<File> splitFileIntoPieces(File source, long maxPieceBitesSize) {
        List<File> resultList = new LinkedList<>();
        if (source.length() < maxPieceBitesSize) {
            resultList.add(source);
        } else {
            resultList = splitFile(source);
            try {
                while (!splitFilesLessThenMaxSize(maxPieceBitesSize)) {
                    List<File> newSplitFilesList = new LinkedList<>();
                    for (File splitFile : resultList) {
                        newSplitFilesList.addAll(splitFile(splitFile));
                        if (!splitFile.delete()) {
                            throw new IOException("Не удалось удалить файл" + splitFile.getPath());
                        }
                    }
                    resultList = newSplitFilesList;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return resultList;
    }

    private boolean splitFilesLessThenMaxSize(long size) {
        File[] splitDirFiles = new File(pathToSplitDir).listFiles();
        boolean result = true;
        if (splitDirFiles != null) {
            for (File checkingFile: splitDirFiles) {
                if (checkingFile.length() > size) {
                    result = false;
                    break;
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    private List<File> splitFile(File file) {
        long middlePointPos = file.length() / 2;
        File[] splitDirFiles = new File(pathToSplitDir).listFiles();
        List<File> fileList = new LinkedList<>();
        if (splitDirFiles != null) {
            int splitDirFilesCount = splitDirFiles.length;
            File firstFile = new File(String.format("%s%sfirst%s.txt", pathToSplitDir, fs, ++splitDirFilesCount));
            File secondFile = new File(String.format("%s%ssecond%s.txt", pathToSplitDir, fs, ++splitDirFilesCount));
            try {
                RandomAccessFile sourceFileRandomAccess = new RandomAccessFile(file, "r");
                FileOutputStream firstFilePartRandomAccess = new FileOutputStream(firstFile);
                FileOutputStream secondFilePartRandomAccess = new FileOutputStream(secondFile);
                while (sourceFileRandomAccess.getFilePointer() < file.length()) {
                    String line = sourceFileRandomAccess.readLine() + ln;
                    long filePointerPos = sourceFileRandomAccess.getFilePointer();
                    if (filePointerPos <= middlePointPos) {
                        firstFilePartRandomAccess.write(line.getBytes(Charset.forName("UTF-8")));
                    } else {
                        secondFilePartRandomAccess.write(line.getBytes(Charset.forName("UTF-8")));
                    }
                }
                sourceFileRandomAccess.close();
                firstFilePartRandomAccess.close();
                secondFilePartRandomAccess.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileList.add(firstFile);
            fileList.add(secondFile);
        }
        return fileList;
    }

    private void clearSplitDir() {
        clearDir(pathToSplitDir);
    }

    private void clearDir(String path) {
        File[] folderFiles = new File(path).listFiles();
        boolean filesExist = folderFiles != null && folderFiles.length > 0;
        if (filesExist) {
            for (File file : folderFiles) {
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
