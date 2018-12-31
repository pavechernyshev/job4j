package ru.job4j.pool;

import org.hamcrest.core.Is;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicIntegerArray;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class ThreadPoolTest {

    @Test
    public void whenTest() {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);
        ThreadPool threadPool = new ThreadPool();
        threadPool.startThreads();
        for (int tasksCount = 0; tasksCount < 10; tasksCount++) {
            final int tasksCountFinal = tasksCount;
            Thread task = new Thread(() -> {
                atomicIntegerArray.set(tasksCountFinal, tasksCountFinal);
            });
            try {
                task.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPool.work(task);
        }
        while (threadPool.isAlive()) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!threadPool.hasWork()) {
                threadPool.shutdown();
            }
        }
        assertThat(atomicIntegerArray.get(0), Is.is(0));
        assertThat(atomicIntegerArray.get(1), Is.is(1));
        assertThat(atomicIntegerArray.get(2), Is.is(2));
        assertThat(atomicIntegerArray.get(3), Is.is(3));
        assertThat(atomicIntegerArray.get(4), Is.is(4));
        assertThat(atomicIntegerArray.get(5), Is.is(5));
        assertThat(atomicIntegerArray.get(6), Is.is(6));
        assertThat(atomicIntegerArray.get(7), Is.is(7));
        assertThat(atomicIntegerArray.get(8), Is.is(8));
        assertThat(atomicIntegerArray.get(9), Is.is(9));
    }

    @Ignore
    @Test
    public void whenTestIsAllThreadWaiting() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();
        ThreadPool pool = new ThreadPool();
        pool.work(() -> map.put(1, 1));
        pool.work(() -> map.put(2, 2));
        pool.work(() -> map.put(3, 3));
        pool.work(() -> map.put(4, 4));
        pool.work(() -> map.put(5, 5));
        pool.work(() -> map.put(6, 6));
        pool.work(() -> map.put(7, 7));
        pool.work(() -> map.put(8, 8));
        pool.work(() -> map.put(9, 9));
        pool.work(() -> map.put(10, 10));
        pool.work(() -> map.put(11, 11));
        pool.work(() -> map.put(12, 12));
        pool.work(() -> map.put(13, 13));
        pool.work(() -> map.put(14, 14));
        pool.work(() -> map.put(15, 15));
        pool.startThreads();
        while (pool.hasWork() || !pool.isAllThreadsWaiting()) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pool.shutdown();
        assertThat(map.size(), Is.is(15));
    }

}