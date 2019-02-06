package ru.job4j.socket.app;

import java.util.Scanner;

public class ConsoleInput implements InputClient {

    private Scanner scanner = new Scanner(System.in); //Композиция
    @Override
    public String ask(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    @Override
    public int ask(String question, int[] range) {
        int key = Integer.valueOf(ask(question));
        boolean exist = false;
        for (int value: range) {
            if (value == key) {
                exist = true;
            }
        }
        if (!exist) {
            throw new MenuOutException("out of menu range");
        }
        return key;
    }
}
