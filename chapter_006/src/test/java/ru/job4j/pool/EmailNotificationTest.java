package ru.job4j.pool;

import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class EmailNotificationTest {

    @Test
    public void whenTestEmails() {
        EmailNotification emailNotification = new EmailNotification();
        User userPavel = new User("Pavel", "Pavel@test.ru");
        User userIlya = new User("Ilya", "Ilya@test.ru");
        User userAndrey = new User("Andrey", "Andrey@test.ru");
        User userVladimir = new User("Vladimir", "Vladimir@test.ru");
        emailNotification.emailTo(userPavel);
        emailNotification.emailTo(userIlya);
        emailNotification.emailTo(userAndrey);
        emailNotification.emailTo(userVladimir);
        emailNotification.close();
        while (!emailNotification.isTerminated()) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}