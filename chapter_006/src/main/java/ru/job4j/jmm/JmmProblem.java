package ru.job4j.jmm;

public class JmmProblem {

    public static void main(String[] args) {
        Variable var = new Variable(0);
        Thread first = new Thread(new PrintVariable("first", var));
        Thread second = new Thread(new PrintVariable("second", var));
        Thread theard = new Thread(new PrintVariable("theard", var));
        Thread fourth = new Thread(new PrintVariable("fourth", var));
        Thread five = new Thread(new PrintVariable("five", var));
        first.start();
        second.start();
        theard.start();
        fourth.start();
        five.start();
    }
}
