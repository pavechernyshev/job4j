package ru.job4j.jmm;

public class JmmProblem {

    public static void main(String[] args) {
        Variable var = new Variable(0);
        Thread first = new Thread(new PrintVariable("first", var));
        Thread second = new Thread(new PrintVariable("second", var));
        Thread thread = new Thread(new PrintVariable("thread", var));
        Thread fourth = new Thread(new PrintVariable("fourth", var));
        Thread five = new Thread(new PrintVariable("five", var));
        first.start();
        second.start();
        thread.start();
        fourth.start();
        five.start();
    }
}
