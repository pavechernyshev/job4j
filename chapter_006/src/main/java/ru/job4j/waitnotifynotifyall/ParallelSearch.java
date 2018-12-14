package ru.job4j.waitnotifynotifyall;

import static java.lang.Thread.sleep;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        final Thread consumer = new Thread(
                () -> {
                    while (!queue.isInterrupted()) {
                        System.out.println(queue.poll());
                    }
                }
        );
        consumer.start();
        final Thread produser = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        produser.start();

        try {
            while (produser.isAlive()) {
                sleep(100);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        queue.interrupt();// не работает
    }
}
