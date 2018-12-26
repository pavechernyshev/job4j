package ru.job4j.pool;
import ru.job4j.waitnotifynotifyall.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(100);

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
            threads.add(t);
        }
    }

    public void startThreads() {
        for(Thread thread: threads) {
            thread.start();
        }
    }

    public boolean isAlive() {
        boolean alive = false;
        for (Thread thread: threads) {
            if (thread.isAlive()) {
                alive = true;
                break;
            }
        }
        return alive;
    }

    public boolean isAllThreadsWaiting() {
        boolean waiting = true;
        for (Thread thread: threads) {
            if (thread.getState() != Thread.State.WAITING) {
                waiting = false;
                break;
            }
        }
        return waiting;
    }

    public List<Thread.State> getThreadsState() {
        List<Thread.State> states = new LinkedList<>();
        for (Thread thread: threads) {
            states.add(thread.getState());
        }
        return states;
    }

    public boolean hasWork() {
        return !tasks.isEmpty();
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
