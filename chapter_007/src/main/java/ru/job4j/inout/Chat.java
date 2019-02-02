package ru.job4j.inout;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Chat {

    private final String stopWord = "стоп";
    private final String continueWord = "продолжить";
    private final String exitWord = "закончить";
    private final String askUserPhrase = "введите слово или фразу: ";
    private final String pathToFileLog = System.getProperty("user.dir") + "\\chapter_007\\src\\main\\java\\ru\\job4j\\inout\\log.txt";
    private final String pathToFilePhrases = System.getProperty("user.dir") + "/chapter_007/src/main/java/ru/job4j/inout/phrases.txt";
    private ArrayList<String> phrasesList;

    private Input input;

    public static void main(String[] args) {
        new Chat().startup();
    }

    Chat() {
        input = new ConsoleInput();
    }

    public void startup() {
        phrasesList = (ArrayList<String>) getLinesFromFile(new File(pathToFilePhrases));
        String userPhrase = askUser(askUserPhrase);
        boolean isStop = false;
        while (!userPhrase.equals(exitWord)) {
            switch (userPhrase) {
                case stopWord: isStop = true; break;
                case continueWord: isStop = false; break;
                default: break;
            }
            if (!isStop) {
                showAnswer(getRandomPhrase());
            }
            userPhrase = askUser(askUserPhrase);
        }
    }

    private String askUser(String ask) {
        writeToLog(ask);
        String userAnswer = input.ask(ask);
        writeToLog(userAnswer);
        return userAnswer;
    }

    private void showAnswer(String answer) {
        writeToLog(answer);
        System.out.println(answer);
    }

    private String getRandomPhrase() {
        String res = "";
        if (phrasesList.size() > 1) {
            int randomIndex = (int) (Math.random() * this.phrasesList.size());
            if (randomIndex > phrasesList.size()) {
                randomIndex--;
            }
            res = phrasesList.get(randomIndex);
        }
        return res;
    }

    private void writeToLog(String log) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(pathToFileLog, "rw")) {
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.write(String.format("%s\n", log).getBytes(Charset.forName("UTF-8")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getLinesFromFile(File file) {
        List<String> phrasesList = new ArrayList<>(100);
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            while (scanner.hasNextLine()) {
                phrasesList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return phrasesList;
    }
}
