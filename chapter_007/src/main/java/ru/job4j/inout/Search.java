package ru.job4j.inout;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Search {
    public List<File> files(String startDirPath, List<String> exts) {
        List<File> resultFiles = new LinkedList<>();
        File startDir = new File(startDirPath);
        boolean startDataCorrect = startDir.isDirectory() && startDir.listFiles() != null && exts.size() > 0;
        if (startDataCorrect) {
            Queue<File> filesForCheck = new LinkedList<>(Arrays.asList(startDir.listFiles()));
            while (filesForCheck.size() > 0) {
                File checkingFile = filesForCheck.poll();
                if (checkingFile.isDirectory()) {
                    File[] curDirFiles = checkingFile.listFiles();
                    if (curDirFiles != null) {
                        filesForCheck.addAll(Arrays.asList(curDirFiles));
                    }
                } else if (checkingFile.isFile()) {
                    String ext = getFileExtension(checkingFile);
                    if (ext != null && exts.contains(ext)) {
                        resultFiles.add(checkingFile);
                    }
                }
            }
        }
        return resultFiles;
    }

    public List<File> filesWithoutExcluded(String startDirPath, List<String> excludeFiles) {
        File startDir = new File(startDirPath);
        List<String> excludeExt = new LinkedList<>();
        for (String fileName: excludeFiles) {
            if (fileName.substring(0, 1).equals("*")) {
                excludeExt.add(fileName.substring(2));
                excludeFiles.remove(fileName);
            }
        }
        List<File> resultFiles = new LinkedList<>();
        boolean startDataCorrect = startDir.isDirectory() && startDir.listFiles() != null;
        if (startDataCorrect) {
            Queue<File> filesForCheck = new LinkedList<>(Arrays.asList(startDir.listFiles()));
            while (filesForCheck.size() > 0) {
                File checkingFile = filesForCheck.poll();
                if (checkingFile.isDirectory()) {
                    File[] curDirFiles = checkingFile.listFiles();
                    if (curDirFiles != null) {
                        filesForCheck.addAll(Arrays.asList(curDirFiles));
                    }
                } else if (checkingFile.isFile()) {
                    String ext = getFileExtension(checkingFile);
                    boolean canAddFile = !excludeFiles.contains(checkingFile.getName()) && !excludeExt.contains(ext);
                    if (canAddFile) {
                        resultFiles.add(checkingFile);
                    }
                }
            }
        }
        return resultFiles;
    }

    /**
     * если в имени файла есть точка и она не является первым символом в названии файла
     * то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
     * иначе null
     *
     * @param file файл с интересуемым расширением
     * @return String|null
     */
    private String getFileExtension(File file) {
        String fileName = file.getName();
        String res = null;
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            res = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return res;
    }
}
