package ru.job4j.inout;

import java.util.Scanner;

public class ConsoleInput implements Input {
    @Override
    public String ask(String question) {
        System.out.print(question);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
