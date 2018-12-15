package ru.job4j.waitnotifynotifyall;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int maxSize;
    private final Object lock = new Object();
    private boolean isInterrupted = false;

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void interrupt() {
        isInterrupted = true;
    }

    public boolean isInterrupted() {
        return isInterrupted;
    }

    public void offer(T value) {
        synchronized (this.lock) {
            while (queue.size() == this.maxSize) {
                try {
                    lock.wait();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            queue.add(value);
            this.lock.notify();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this.lock) {
            while (queue.size() == 0) {
                lock.wait();
            }
            T res = queue.poll();
            this.lock.notify();
            return res;
        }
    }
}
