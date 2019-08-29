package ru.job4j.demonstrtiongc;

// заголовки для объекта - 8 байт
public class User {

    private String name; //примерно 40 байт + 2 байта на символ
    private int id; // 4 байта

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println(String.format("Finalise %s | %d", this.name, this.id));
    }
}
