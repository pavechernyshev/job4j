package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );


    /**
     * должен через ExecutorService отправлять почту.
     * должен брать данные пользователя и подставлять в шаблон
     * subject = Notification {username} to email {email}.
     * body = Add a new event to {username}
     * @param user пользователь
     */
    public void emailTo(User user) {
        final String subject = String.format("Notification %s to email %s", user.getName(), user.getEmail());
        final String body = String.format("Add a new event to %s", user.getName());
        final String email = user.getEmail();
        pool.submit(() -> {
            this.send(subject, body, email);
        });
    }

    public boolean isTerminated() {
        return pool.isTerminated();
    }

    /**
     * должен закрыть pool
     */
    public void close() {
        pool.shutdown();
    }

    /**
     * должен быть пустой
     * @param subject уведомление
     * @param body тело
     * @param email почта
     */
    public void send(String subject, String body, String email) {

    }
}
