package ru.job4j.socket.app;

public interface UserAction {
    int key();

    void execute(InputClient input, ClientApi clientApi);

    String info();
}
