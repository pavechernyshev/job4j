package ru.job4j.inout;

import java.util.LinkedList;
import java.util.List;

public class Args {
    private String startDirPath;
    private List<String> excludeFilesList;
    private String outputFileName;
    private String[] args;
    private List<String> startDirPathKeys;
    private List<String> excludeFilesKeys;
    private List<String> outputFileNameKeys;

    Args(String[] args) {
        this.args = args;
        outputFileNameKeys = new LinkedList<>();
        outputFileNameKeys.add("-o");
        outputFileNameKeys.add("-output");
        startDirPathKeys = new LinkedList<>();
        startDirPathKeys.add("-d");
        startDirPathKeys.add("-directory");
        excludeFilesKeys = new LinkedList<>();
        excludeFilesKeys.add("-e");
        excludeFilesKeys.add("-exclude");
    }

    public void init() {
        for (int i = 0; i < args.length - 1; i++) {
            String curValue = args[i];
            String nextValue = args[i + 1];
            if (startDirPathKeys.contains(curValue) && !isKey(nextValue)) {
                startDirPath = nextValue;
            }
            if (outputFileNameKeys.contains(curValue) && !isKey(nextValue)) {
                outputFileName = nextValue;
            }
            if (excludeFilesKeys.contains(curValue) && !isKey(nextValue)) {
                int excludeFileIndex = i + 1;
                String excludeFileValue = args[excludeFileIndex];
                while (excludeFileIndex < args.length && !isKey(excludeFileValue)) {
                    excludeFilesList.add(excludeFileValue);
                    excludeFileValue = args[excludeFileIndex++];
                }
            }
        }
    }

    public String getStartDirPath() {
        return startDirPath;
    }

    public void setStartDirPath(String startDirPath) {
        this.startDirPath = startDirPath;
    }

    public List<String> getExcludeFilesList() {
        return excludeFilesList;
    }

    private boolean isKey(String key) {
        return key.substring(0, 1).equals("-");
    }
}
