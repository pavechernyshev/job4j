package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class StartUITest {
    // поле содержит дефолтный вывод в консоль.
    private final PrintStream stdout = System.out;
    // буфер для результата.
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    // line separator
    private final String ls = System.lineSeparator();

    private String getMenu() {
        return new StringBuilder()
                .append("Меню.").append(this.ls)
                .append("0. - Добавить новую заявку").append(this.ls)
                .append("1. - Показать заявки").append(this.ls)
                .append("2. - Редактировать заявку").append(this.ls)
                .append("3. - Удалить заявку").append(this.ls)
                .append("4. - Найти заявку по идентификатору").append(this.ls)
                .append("5. - Найти заявку по названию").append(this.ls)
                .append("6. - Выйти из программы").append(this.ls)
                //.append("Введите пункт меню : ")
                .toString();
    }

    @Before
    public void loadOutput() {
        System.out.println("execute before method");
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.setOut(this.stdout);
        System.out.println("execute after method");
    }

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();     // создаём Tracker
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});   //создаём StubInput с последовательностью действий
        new StartUI(input, tracker).init();     //   создаём StartUI и вызываем метод init()
        assertThat(tracker.findAll().get(0).getName(), is("test name")); // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc"));
        //создаём StubInput с последовательностью действий(производим замену заявки)
        Input input = new StubInput(new String[]{"2", item.getId(), "test replace", "заменили заявку", "6"});
        // создаём StartUI и вызываем метод init()
        new StartUI(input, tracker).init();
        // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
        assertThat(tracker.findById(item.getId()).getName(), is("test replace"));
    }

    @Test
    public void whenDeleteThenTrackerHasLessOneItem() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc"));
        //Напрямую добавляем заявку
        Item item2 = tracker.add(new Item("test name 2", "desc 2"));
        //создаём StubInput с последовательностью действий(производим замену заявки)
        Input input = new StubInput(new String[]{"3", item2.getId(), "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().size(), is(1));
    }

    @Test
    public void whenDeleteThenTrackerNotFoundByID() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc"));
        //Напрямую добавляем заявку
        Item item2 = tracker.add(new Item("test name 2", "desc 2"));
        //создаём StubInput с последовательностью действий(производим замену заявки)
        Input input = new StubInput(new String[]{"3", item2.getId(), "6"});
        new StartUI(input, tracker).init();
        Item expect = null;
        assertThat(tracker.findById(item2.getId()), is(expect));
    }

    @Test
    public void whenNotDeleteThenTrackerHasLessOneItem() {
        // создаём Tracker
        Tracker tracker = new Tracker();
        //Напрямую добавляем заявку
        Item item = tracker.add(new Item("test name", "desc"));
        //Напрямую добавляем заявку
        Item item2 = tracker.add(new Item("test name 2", "desc 2"));
        //создаём StubInput с последовательностью действий(производим замену заявки)
        Input input = new StubInput(new String[]{"3", item2.getId() + "hash", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().size(), is(2));
    }

    @Test
    public void whenStartAndExit() {
        Input input = new StubInput(new String[]{"6"});
        Tracker tracker = new Tracker();
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(this.getMenu())
                                //.append("Введите пункт меню : ")
                                .toString()
                )
        );
    }

    @Test
    public void whenFindAllThenShowTwoItems() {
        Tracker tracker = new Tracker();
        Item pavel = tracker.add(new Item("pavel", "desc"));
        Item andrey = tracker.add(new Item("andrey", "desc 2"));
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(this.getMenu())
                                .append("------------ Отображение всех заявок --------------")
                                .append(this.ls)
                                .append("id: ").append(pavel.getId())
                                .append("; name: pavel; desc: desc")
                                .append(this.ls)
                                .append("id: ").append(andrey.getId())
                                .append("; name: andrey; desc: desc 2")
                                .append(this.ls)
                                .append(this.getMenu())
                                .toString()
                )
        );
    }

    @Test
    public void whenFindAllThenShowNotItems() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(this.getMenu())
                                .append("------------ Отображение всех заявок --------------")
                                .append(this.ls)
                                .append("------------ Заявок нет. --------------")
                                .append(this.ls)
                                .append(this.getMenu())
                                .toString()
                )
        );
    }

    @Test
    public void whenFindByIdThenFound() {
        Tracker tracker = new Tracker();
        Item pavel = tracker.add(new Item("pavel", "desc"));
        Item andrey = tracker.add(new Item("andrey", "desc 2"));
        Input input = new StubInput(new String[]{"4", andrey.getId(), "6"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(this.getMenu())
                                .append("------------ Поиск заявки по идентификатору --------------")
                                .append(this.ls)
                                .append("id: ").append(andrey.getId())
                                .append("; name: andrey; desc: desc 2")
                                .append(this.ls)
                                .append(this.getMenu())
                                .toString()
                )
        );
    }

    @Test
    public void whenFindByIdThenNotFound() {
        Tracker tracker = new Tracker();
        Item pavel = tracker.add(new Item("pavel", "desc"));
        Item andrey = tracker.add(new Item("andrey", "desc 2"));
        Input input = new StubInput(new String[]{"4", "-----", "6"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(this.getMenu())
                                .append("------------ Поиск заявки по идентификатору --------------")
                                .append(this.ls)
                                .append("------------ Ну удалось найти заявку --------------")
                                .append(this.ls)
                                .append(this.getMenu())
                                .toString()
                )
        );
    }

    @Test
    public void whenFindByNameThenFound() {
        Tracker tracker = new Tracker();
        Item pavel = tracker.add(new Item("pavel", "desc"));
        Item andrey = tracker.add(new Item("andrey", "desc 2"));
        Input input = new StubInput(new String[]{"5", "pavel", "6"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(this.getMenu())
                                .append("------------ Поиск заявки по наименованию --------------")
                                .append(this.ls)
                                .append("id: ").append(pavel.getId())
                                .append("; name: pavel; desc: desc")
                                .append(this.ls)
                                .append(this.getMenu())
                                .toString()
                )
        );
    }

    @Test
    public void whenFindByNameThenNotFound() {
        Tracker tracker = new Tracker();
        Item pavel = tracker.add(new Item("pavel", "desc"));
        Item andrey = tracker.add(new Item("andrey", "desc 2"));
        Input input = new StubInput(new String[]{"5", "pave", "6"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(this.getMenu())
                                .append("------------ Поиск заявки по наименованию --------------")
                                .append(this.ls)
                                .append("------------ Ну удалось найти заявку --------------")
                                .append(this.ls)
                                .append(this.getMenu())
                                .toString()
                )
        );
    }
}
