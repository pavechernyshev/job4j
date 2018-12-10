package ru.job4j.wait_notify_notifyall;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    //todo: объект пользователя, для последующего блокирования

    //todo: объект производителя, для последующего блокирования

    // Признак блокировки пользователя
    // Признак блокировки производителя


    public void offer(T value) {
        //Потокобезопасный блок
            /* Добавить товар, если место закончилось, заблокировать производителей,
             разблокировать пользователей в любом случае*/
    }

    public T poll() {
        //Потокобезопасный блок
            /* проверить наличите, если товаров нет, заблокировать пользователе, если товар есть,
            разблокировать производтелей*/
        return null;
    }
}
