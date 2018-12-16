package ru.job4j.nonblocking;

public class VolatileTest {

    private static volatile int myInt = 0;

    public static void main(String[] args) {
        new ChangeListener().start();
        new ChangeMaker().start();
    }

    static class ChangeListener extends Thread {
        @Override
        public void run() {
            int localValue = myInt;
            while (localValue < 5) {
                if (localValue != myInt) {
                    System.out.println(String.format("Got Change for myInt : %s", myInt));
                    localValue = myInt;
                }
            }
        }
    }

    static class ChangeMaker extends Thread {
        @Override
        public void run() {
            int localValue = myInt;
            while (myInt < 5) {
                System.out.println(String.format("Incrementing myInt to %s", localValue + 1));
                myInt = ++localValue;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
