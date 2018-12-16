package ru.job4j.nonblocking;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.core.Is.is;

public class CacheTest {
    @Test
    public void whenThrowException() throws InterruptedException {
        AtomicReference<Exception> ex = new AtomicReference<>();
        Thread thread = new Thread(
                () -> {
                    try {
                        throw new RuntimeException("Throw Exception in Thread");
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        thread.start();
        thread.join();
        Assert.assertThat(ex.get().getMessage(), is("Throw Exception in Thread"));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        cache.add(new Base(2, 1));
        cache.add(new Base(3, 1));
        cache.update(new Base(2, 1));
        cache.update(new Base(1, 1));
    }

    @Test (expected = OptimisticException.class)
    public void whenTrowOptimisticException() {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        cache.update(new Base(1, 1));
        cache.update(new Base(1, 1));
    }

}