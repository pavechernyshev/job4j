package ru.job4j.socket.app;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class FileController implements FileInspector {
    private String curDirPath = System.getProperty("user.dir");

    @Override
    public boolean goUp() {
        String pathSeparator = "\\";
        boolean result = false;
        int lastIndexOfPathSeparator = this.curDirPath.lastIndexOf(pathSeparator);
        if (lastIndexOfPathSeparator > 0) {
            this.curDirPath = this.curDirPath.substring(0, lastIndexOfPathSeparator);
            result = true;
        }
        return result;
    }

    @Override
    public boolean goToDir(String dirName) {
        boolean res = false;
        if (dirName.length() > 0) {
            String distDirPath = mergePathAndName(this.curDirPath, dirName);
            File distFile = new File(distDirPath);
            if (distFile.isDirectory()) {
                this.curDirPath = distDirPath;
                res = true;
            }
        }
        return res;
    }

    @Override
    public List<File> getCurDirContent() {
        File file = new File(this.curDirPath);
        return new LinkedList<>(Arrays.asList(Objects.requireNonNull(file.listFiles())));
    }

    @Override
    public File getFile(String fileName) {
        File file = new File(fileName);
        File res = null;
        if (file.exists() && file.isFile()) {
            res = file;
        }
        return res;
    }

    @Override
    public boolean loadFile(String fileName, InputStream inputStream) {
        String distFilePath = mergePathAndName(this.curDirPath, fileName);
        File file = new File(distFilePath);
        boolean res = false;
        if (!file.exists()) {
            try (Scanner scanner = new Scanner(inputStream);
                 FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                while (scanner.hasNextLine()) {
                    String lineWithDelimiter = String.format("%s%s", scanner.nextLine(), System.lineSeparator());
                    fileOutputStream.write(lineWithDelimiter.getBytes(Charset.forName("UTF-8")));
                }
                res = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private String mergePathAndName(String path, String name) {
        StringBuilder distPath = new StringBuilder();
        distPath.append(path).append("\\").append(name);
        return distPath.toString();
    }
}
