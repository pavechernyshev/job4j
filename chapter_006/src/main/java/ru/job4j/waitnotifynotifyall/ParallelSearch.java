package ru.job4j.waitnotifynotifyall;

import java.beans.IntrospectionException;

import static java.lang.Thread.sleep;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        final Thread consumer = new Thread(
                ()  -> {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            System.out.println(queue.poll());
                        }
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                        System.out.println(String.format("Finish consumer thread: %s", Thread.currentThread().getId()));
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
            produser.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        consumer.interrupt();
    }
}
