package ru.job4j.socket.app;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface FileInspector {
    boolean goUp();
    boolean goToDir(String dirName);
    List<File> getCurDirContent();
    File getFile(String fileName);
    boolean loadFile(String fileName, InputStream inputStream);
}
