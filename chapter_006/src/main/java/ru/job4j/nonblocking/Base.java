package ru.job4j.nonblocking;

public class Base {
    private int id;
    private int version;

    Base(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void upVersion() {
        this.version++;
    }
}
