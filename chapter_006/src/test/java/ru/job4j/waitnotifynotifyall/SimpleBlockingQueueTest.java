package ru.job4j.waitnotifynotifyall;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;
import static org.junit.Assert.*;

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
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }
        consumer.interrupt();
        consumer2.interrupt();
        consumer3.interrupt();
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            queue::offer
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, Is.is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}
