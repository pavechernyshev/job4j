package ru.job4j.pool;
import ru.job4j.waitnotifynotifyall.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    while (!this.isInterrupted()) {
                        try {
                            Thread task = new Thread(tasks.poll());
                            task.start();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            this.interrupt();
                        }
                    }
                }
            };
            t.start();
            threads.add(t);
        }
    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread: threads) {
            thread.interrupt();
        }
    }
}
