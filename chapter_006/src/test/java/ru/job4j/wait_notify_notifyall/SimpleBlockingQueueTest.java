package ru.job4j.wait_notify_notifyall;

import org.junit.Test;

public class SimpleBlockingQueueTest {
    @Test
    public void whenTestTenItemsStack() {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(10);
        Thread produser = new Thread(new Produser(simpleBlockingQueue));
        Thread consumer = new Thread(new Consumer(simpleBlockingQueue));
        Thread consumer2 = new Thread(new Consumer(simpleBlockingQueue));
        Thread consumer3 = new Thread(new Consumer(simpleBlockingQueue));
        produser.start();
        consumer.start();
        consumer2.start();
        consumer3.start();
        try {
            produser.join();
            consumer.join();
            consumer2.join();
            consumer3.join();
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }
    }
}