package ru.job4j.calculator;

public interface Input {
    String askAction(String question);
    double askDigit(String question);
}
