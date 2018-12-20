package ru.job4j.pool;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class ThreadPoolTest {

    @Test
    public void whenTest() {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);
        ThreadPool threadPool = new ThreadPool();
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
        threadPool.shutdown();
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

}