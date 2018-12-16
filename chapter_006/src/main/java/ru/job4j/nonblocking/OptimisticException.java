package ru.job4j.nonblocking;

public class OptimisticException extends RuntimeException {
    public OptimisticException() {
        super("Версии не равны");
    }
}
