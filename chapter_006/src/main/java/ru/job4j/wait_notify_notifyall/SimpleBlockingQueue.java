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
    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    //объект пользователя, для последующего блокирования
    private Object producer = new Object();
    //объект производителя, для последующего блокирования
    private Object customer = new Object();


    // Признак блокировки пользователя
    private boolean isCustomerBlock = false;

    // Признак блокировки производителя
    private boolean isProducerBlock = false;

    public synchronized void offer(T value) {
        while (this.isProducerBlock) {
            try {
                producer.wait();
            }
            catch (InterruptedException ie) {
                System.out.println(ie.getMessage());
            }
        }
        this.queue.add(value);
        this.isCustomerBlock = false;
        this.customer.notify();
        if (this.queue.size() == this.maxSize) {
            this.isProducerBlock = true;
        }
    }

    public synchronized T poll() {
        while (this.isCustomerBlock) {
            try {
                customer.wait();
            }
            catch (InterruptedException ie) {
                System.out.println(ie.getMessage());
            }
        }
        T res = null;
        if (queue.size() == 0) {
            isCustomerBlock = true;
        }
        else {
            res = queue.poll();
            this.producer.notify();
            this.isProducerBlock = false;
        }
        //Потокобезопасный блок
            /* проверить наличите, если товаров нет, заблокировать пользователе, если товар есть,
            разблокировать производтелей*/
        return res;
    }
}
