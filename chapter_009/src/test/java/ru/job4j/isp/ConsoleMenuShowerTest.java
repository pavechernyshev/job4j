package ru.job4j.isp;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ConsoleMenuShowerTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final String ls = System.lineSeparator();

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
    }

    @Test
    public void show() {
        IStructItem task1 = new StructItem("Задача 1.");
        IStructItem task11 = new StructItem("Задача 1.1");
        IStructItem task111 = new StructItem("Задача 1.1.1");
        IStructItem task112 = new StructItem("Задача 1.1.2");
        IStructItem task12 = new StructItem("Задача 1.2");
        IStructItem task2 = new StructItem("Задача 2.");
        IStructItem task3 = new StructItem("Задача 3.");
        task11.addSubItem(task111);
        task11.addSubItem(task112);
        task1.addSubItem(task11);
        task1.addSubItem(task12);
        ConsoleMenuShower consoleMenuShower = new ConsoleMenuShower();
        consoleMenuShower.setLvlSeparator("---");
        consoleMenuShower.addRootItem(task1);
        consoleMenuShower.addRootItem(task2);
        consoleMenuShower.addRootItem(task3);
        consoleMenuShower.show();
        assertThat(
                new String(out.toByteArray()),
                is(
                "Задача 1." + this.ls
                        + "---Задача 1.1" + this.ls
                        + "------Задача 1.1.1" + this.ls
                        + "------Задача 1.1.2" + this.ls
                        + "---Задача 1.2" + this.ls
                        + "Задача 2." + this.ls
                        + "Задача 3." + this.ls
                )
        );
    }
}