package ru.job4j.jmm;

public class PrintVariable implements Runnable{
    private final String name;
    private Variable var;

    PrintVariable(String name, Variable var) {
        this.name = name;
        this.var = var;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
                var.setValue(var.getValue() + 1);
                System.out.println(String.format("%s: %s", this.name, var.getValue()));
        }
    }
}
