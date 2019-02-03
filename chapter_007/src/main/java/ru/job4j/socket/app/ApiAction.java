package ru.job4j.socket.app;

public interface ApiAction {

    String methodName();

    void execute(Output output, FileInspector fileInspector);

    String info();
}
