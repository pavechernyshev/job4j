package ru.job4j.socket.app;

public interface InputClient {
    String ask(String question);

    int ask(String question, int[] range);
}
