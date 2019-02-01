package ru.job4j.inout;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public static void main(String[] args) {
        Args argsParser = new Args(args);
        argsParser.init();
        new Zip().archive(
            argsParser.getStartDirPath(),
            argsParser.getExcludeFilesList(),
            argsParser.getOutputFileName()
        );
    }

    public void archive(String startDirPath, List<String> excludeFiles, String distFileName) {
        Search search = new Search();
        List<File> filesList = search.filesWithoutExcluded(startDirPath, excludeFiles);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(distFileName))) {
            String distFilePath = mergePathAndFileName(startDirPath, distFileName);
            for (File file: filesList) {
                if (file.getPath().equals(distFilePath)) {
                    continue;
                }
                System.out.print(String.format("%s ... archiving", file.getPath()));
                String relativeFilePath = getRelativeFilePath(startDirPath, file);
                zipOutputStream.putNextEntry(new ZipEntry(relativeFilePath));
                FileInputStream fileInputStream = new FileInputStream(file);
                write(fileInputStream, zipOutputStream);
                zipOutputStream.closeEntry();
                System.out.println(" ..... was archived");
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

    private String mergePathAndFileName(String startDirPath, String fileName) {
        String lastPathSymbol = startDirPath.substring(startDirPath.length() - 1);
        String fistFileNameSymbol = startDirPath.substring(0, 1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startDirPath);
        String separator = "\\";
        if (lastPathSymbol.equals(separator)) {
            stringBuilder.append(separator);
        }
        if (fistFileNameSymbol.equals(separator)) {
            fileName = fileName.substring(1);
        }
        stringBuilder.append(fileName);
        return stringBuilder.toString();
    }

    private String getRelativeFilePath(String deletePath, File file) {
        String res = file.getPath().replace(deletePath, "");
        if (res.substring(0, 1).equals("\\") || res.substring(0, 1).equals("/")) {
            res = res.substring(1);
        }
        return res;
    }
}
