package ru.job4j.exam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.*;

public class Find {
    public static void main(String[] args) throws IOException {
        IArg argDir = new BaseArg("-d", new NotNullArgValidator(), true, "-d - директория в которая начинать поиск");
        IArg argName = new BaseArg("-n", new NotNullArgValidator(), true, "-n - имя файл, маска, либо регулярное выражение.");
        IArg argMask = new BaseArg("-m", new NullArgValidator(), false, "-m - искать по маске");
        IArg argFullNameMatch = new BaseArg("-f", new NullArgValidator(), false, "-f - полное совпадение имени");
        IArg argRegular = new BaseArg("-r", new NullArgValidator(), false, "-r - регулярное выражение");
        IArg argOutputInFile = new BaseArg("-o", new NotNullArgValidator(), false, "-o - результат записать в файл.");
        Arguments arguments = new Arguments();
        arguments.setArgument(argDir);
        arguments.setArgument(argName);
        arguments.setArgument(argMask);
        arguments.setArgument(argFullNameMatch);
        arguments.setArgument(argRegular);
        arguments.setArgument(argOutputInFile);
        arguments.execute(args);
        if (!arguments.isValid()) {
            System.out.println(arguments.help());
            throw new IllegalArgumentException("Не валидные значения");
        }
        List<String> usageArguments = arguments.getUsageArgsKeys();
        IFind methodSearch = null;
        Find find = new Find();
        if (usageArguments.contains(argMask.getKey())) {
            methodSearch = find.new Mask();
        } else if (usageArguments.contains(argFullNameMatch.getKey())) {
            methodSearch = find.new FullMatch();
        } else if (usageArguments.contains(argRegular.getKey())) {
            methodSearch = find.new Reg();
        }
        if (methodSearch == null) {
            System.out.println(arguments.help());
            throw new IllegalArgumentException("Не определен метод поиска");
        }
        methodSearch.setMask(argName.getValue());
        List<File> searchResult = find.start(argDir.getValue(), methodSearch);
        find.printFiles(searchResult);
        if (!argOutputInFile.getValue().isEmpty()) {
            find.writeToLog(searchResult, argOutputInFile.getValue());
        }
    }

    public List<File> start(String dir, IFind methodSearch) {
        List<File> searchResult = new LinkedList<>();
        File startDir = new File(dir);
        File[] filesOfStartDir = startDir.listFiles();
        boolean startDataCorrect = startDir.isDirectory() && filesOfStartDir != null;
        if (!startDataCorrect) {
            throw new IllegalArgumentException("Директория не найдена или пуста");
        }
        Queue<File> filesForCheck = new LinkedList<>(Arrays.asList(filesOfStartDir));
        while (filesForCheck.size() > 0) {
            File checkingFile = filesForCheck.poll();
            if (checkingFile.isDirectory()) {
                File[] curDirFiles = checkingFile.listFiles();
                filesForCheck.addAll(Arrays.asList(Objects.requireNonNull(curDirFiles)));
            } else if (checkingFile.isFile()) {
                if (methodSearch.isEqual(checkingFile.getName())) {
                    searchResult.add(checkingFile);
                }
            }
        }
        return searchResult;
    }

    private void printFiles(List<File> files) {
        for (File file: files) {
            Path path = file.toPath();
            System.out.println(path);
        }
    }

    private void writeToLog(List<File> files, String fileLogName) throws IOException {
        File file = new File(fileLogName);
        boolean fileCreated = file.createNewFile();
        if (!fileCreated) {
            fileCreated = file.exists();
        }
        if (fileCreated) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (File fileLine: files) {
                String line = String.format("%s%s", fileLine.getAbsolutePath(), System.lineSeparator());
                fileOutputStream.write(line.getBytes(Charset.forName("UTF-8")));
            }
        }

    }

    public class Reg implements IFind {

        private String mask;

        @Override
        public void setMask(String mask) {
            this.mask = mask;
        }

        @Override
        public boolean isEqual(String value) {
            return value.matches(this.mask);
        }
    }

    public class Mask implements IFind {

        private String mask;
        @Override
        public void setMask(String mask) {
            this.mask = mask.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
        }

        @Override
        public boolean isEqual(String value) {
            return value.matches(mask);
        }
    }

    public class FullMatch implements IFind {
        private String mask;

        @Override
        public void setMask(String mask) {
            this.mask = mask;
        }

        @Override
        public boolean isEqual(String value) {
            return mask.equals(value);
        }
    }

    public interface IFind {
        void setMask(String mask);
        boolean isEqual(String value);
    }

}
