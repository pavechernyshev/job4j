package shildt.fullmanual.threadutils;

import java.util.concurrent.CountDownLatch;

public class ThreadSimpExec implements Runnable {
    private String name;
    CountDownLatch countDownLatch;

    public ThreadSimpExec(String name, CountDownLatch countDownLatch) {
        this.name = name;
        this.countDownLatch = countDownLatch;
        new Thread(this);
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(String.format("%s: %s", name, i));
            countDownLatch.countDown();
        }
    }
}
