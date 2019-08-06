package ru.job4j.calculator;

import java.util.Scanner;

public class ConsoleInput implements Input {

    private Scanner scanner = new Scanner(System.in); //Композиция

    @Override
    public String askAction(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    @Override
    public double askDigit(String question) {
        System.out.print(question);
        return scanner.nextDouble();
    }
}
