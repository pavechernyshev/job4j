package ru.job4j.wait_notify_notifyall;

import static java.lang.Thread.sleep;

public class Produser implements Runnable {

    private final SimpleBlockingQueue<Integer> simpleBlockingQueue;
    private int count = 0;

    Produser(SimpleBlockingQueue<Integer> simpleBlockingQueue) {
        this.simpleBlockingQueue = simpleBlockingQueue;
    }

    @Override
    public void run() {
        while (count < 30) {
            count++;
            simpleBlockingQueue.offer(count);
        }
    }
}
