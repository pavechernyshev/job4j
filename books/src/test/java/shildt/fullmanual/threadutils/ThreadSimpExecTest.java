package shildt.fullmanual.threadutils;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class ThreadSimpExecTest {
    @Test
    public void whenTestFiveThreads() {
        CountDownLatch countDownLatchOne = new CountDownLatch(5);
        CountDownLatch countDownLatchTwo = new CountDownLatch(5);
        CountDownLatch countDownLatchThree = new CountDownLatch(5);
        CountDownLatch countDownLatchFour = new CountDownLatch(5);
        CountDownLatch countDownLatchFive = new CountDownLatch(5);
        int threadCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        executorService.execute(new ThreadSimpExec("A", countDownLatchOne));
        executorService.execute(new ThreadSimpExec("B", countDownLatchTwo));
        executorService.execute(new ThreadSimpExec("C", countDownLatchThree));
        executorService.execute(new ThreadSimpExec("D", countDownLatchFour));
        executorService.execute(new ThreadSimpExec("E", countDownLatchFive));

        try {
            countDownLatchOne.await();
            countDownLatchTwo.await();
            countDownLatchThree.await();
            countDownLatchFour.await();
            countDownLatchFive.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}