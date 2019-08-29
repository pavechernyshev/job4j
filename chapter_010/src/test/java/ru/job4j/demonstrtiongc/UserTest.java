package ru.job4j.demonstrtiongc;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void getName() throws InterruptedException {
        // указал 4 мб, доступно 2 мб, объект весит 104 байта
        // 1 mb - 20164 объектов
        Runtime runtime = Runtime.getRuntime();
        long lastFreeSize;
        long objSize;
        lastFreeSize = runtime.freeMemory(); // свододная память
        new User("ff");
        objSize = lastFreeSize - runtime.freeMemory(); // размер объекта
        for (int i = 0; i < 20164; i++) {
            new User("ff").setId(i); // 104 байт на объект
        }
        /**
         * пустой объект, без полей весит 16 байт в 32 битной системе и 32 байта в 64 битной системе
         * https://habr.com/ru/post/134102/
         * GC вызывается, чтобы не было переполнения памяти, если мы увеличим объем свободной памяти до 512 мб, то GC на таких объемах не будет вызываться сразу.
         * В данном экспиременте GC начинает активно выхываться после 600-го элемента
         */
    }
}