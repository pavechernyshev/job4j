package ru.job4j.wait_notify_notifyall;

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
    private boolean isCustomerBlock = true;
    private boolean isProducerBlock = false;

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public int size() {
        return queue.size();
    }

    public  void offer(T value) {
        synchronized (this.lock) {
            while (this.isProducerBlock) {
                try {
                    lock.wait();
                } catch (InterruptedException ie) {
                    System.out.println(ie.getMessage());
                }
            }
            this.queue.add(value);
            //System.out.println(String.format("value: %s; size: %s", value, this.queue.size()));
            if (isCustomerBlock) {
                this.isCustomerBlock = false;
                this.lock.notify();
            }
            if (this.queue.size() == this.maxSize) {
                this.isProducerBlock = true;
            }
        }
    }

    public T poll() {
        synchronized (this.lock) {
            while (this.isCustomerBlock) {
                try {
                    lock.wait();
                } catch (InterruptedException ie) {
                    System.out.println(ie.getMessage());
                }
            }
            T res = null;
            if (queue.size() == 0) {
                isCustomerBlock = true;
            } else {
                res = queue.poll();
                //System.out.println(String.format("value: %s; size: %s", res, this.queue.size()));
                if (isProducerBlock) {
                    this.isProducerBlock = false;
                    this.lock.notify();
                }
            }
            return res;
        }
    }
}
