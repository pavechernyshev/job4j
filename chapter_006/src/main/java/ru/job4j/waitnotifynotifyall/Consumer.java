package ru.job4j.waitnotifynotifyall;

import static java.lang.Thread.sleep;

public class Consumer implements Runnable {
    private final SimpleBlockingQueue<Integer> simpleBlockingQueue;
    private int count = 0;

    Consumer(SimpleBlockingQueue<Integer> simpleBlockingQueue) {
        this.simpleBlockingQueue = simpleBlockingQueue;
    }

    @Override
    public void run() {
        try {
            while (count < 10) {
                count++;
                simpleBlockingQueue.poll();
                sleep(3);
            }
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }
    }
}
