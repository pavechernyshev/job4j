package ru.job4j.wait_notify_notifyall;

import static java.lang.Thread.sleep;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        final Thread consumer = new Thread(
                () -> {
                    while (true) {
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
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.println(consumer.isAlive());
        //consumer.notify(); // выбрвсывает исключение
        consumer.interrupt(); // не работает
        System.out.println(consumer.isAlive());
        consumer.stop(); // работает, но метод устаревший
        System.out.println(consumer.isAlive());
    }
}
