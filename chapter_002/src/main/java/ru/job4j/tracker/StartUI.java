package ru.job4j.tracker;

public class StartUI {
    /**
     * Константа меню для добавления новой заявки.
     */
    private static final String ADD = "0";
    private static final String SHOW_ALL_ITEMS = "1";
    private static final String EDIT_ITEM = "2";
    private static final String DELETE_ITEM = "3";
    private static final String FIND_BY_ID = "4";
    private static final String FIND_BY_NAME = "5";

    /**
     * Константа для выхода из цикла.
     */
    private static final String EXIT = "6";
    /**
     * Получение данных от пользователя.
     */
    private final Input input;

    /**
     * Хранилище заявок.
     */
    private final Tracker tracker;

    /**
     * Конструтор инициализирующий поля.
     * @param input ввод данных.
     * @param tracker хранилище заявок.
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основой цикл программы.
     */
    public void init() {
        boolean exit = false;
        while (!exit) {
            this.showMenu();
            String answer = this.input.ask("Введите пункт меню : ");
            if (ADD.equals(answer)) {
                this.createItem();
            } else if (SHOW_ALL_ITEMS.equals(answer)) {
                this.showAllItems();
            } else if (EDIT_ITEM.equals(answer)) {
                this.editItem();
            } else if (DELETE_ITEM.equals(answer)) {
                this.deleteItem();
            } else if (FIND_BY_ID.equals(answer)) {
                this.findById();
            } else if (FIND_BY_NAME.equals(answer)) {
                this.findByName();
            } else if (EXIT.equals(answer)) {
                exit = true;
            }
        }
    }

    /**
     * Метод реализует добавленяи новый заявки в хранилище.
     */
    private void createItem() {
        System.out.println("------------ Добавление новой заявки --------------");
        String name = this.input.ask("Введите имя заявки :");
        String desc = this.input.ask("Введите описание заявки :");
        Item item = new Item(name, desc);
        this.tracker.add(item);
        System.out.println("------------ Новая заявка с getId : " + item.getId() + "-----------");
    }

    private void showAllItems() {
        System.out.println("------------ Отображение всех заявок --------------");
        Item[] items = this.tracker.findAll();
        for (Item item: items) {
            System.out.println(item.toString());
        }
        if (items.length == 0) {
            System.out.println("------------ Заявок нет. --------------");
        }
    }

    private void editItem() {
        System.out.println("------------ Изменение заявки --------------");
        String id = this.input.ask("Введите идентификатор заявки :");
        if (this.isItemExist(id)) {
            String name = this.input.ask("Введите имя заявки :");
            String desc = this.input.ask("Введите описание заявки :");
            Item item = new Item(name, desc);
            item.setId(id);
            this.tracker.replace(id, item);
            System.out.println("------------ Завка изменена --------------");
        } else {
            System.out.println("------------ Ну удалось найти заявку --------------");
        }
    }

    private void deleteItem() {
        System.out.println("------------ Удаление заявки --------------");
        String id = this.input.ask("Введите идентификатор заявки :");
        if (this.isItemExist(id)) {
            this.tracker.delete(id);
            System.out.println("------------ Заявка удалена --------------");
        } else {
            System.out.println("------------ Ну удалось найти заявку --------------");
        }
    }

    private void findById() {
        System.out.println("------------ Поиск заявки по идентификатору --------------");
        String id = this.input.ask("Введите идентификатор заявки :");
        Item item = this.tracker.findById(id);
        if (item != null) {
            System.out.println(item.toString());
        } else {
            System.out.println("------------ Ну удалось найти заявку --------------");
        }
    }

    private void findByName() {
        System.out.println("------------ Поиск заявки по наименованию --------------");
        String name = this.input.ask("Введите имя заявки :");
        Item[] items = this.tracker.findByName(name);
        for (Item item: items) {
            System.out.println(item.toString());
        }
        if (items.length == 0) {
            System.out.println("------------ Ничего не найдено --------------");
        }

    }

    private void showMenu() {
        System.out.println("Меню.");
        System.out.println(ADD + ". - Добавить новую заявку");
        System.out.println(SHOW_ALL_ITEMS + ". - Показать заявки");
        System.out.println(EDIT_ITEM + ". - Редактировать заявку");
        System.out.println(DELETE_ITEM + ". - Удалить заявку");
        System.out.println(FIND_BY_ID + ". - Найти заявку по идентификатору");
        System.out.println(FIND_BY_NAME + ". - Найти заявку по названию");
        System.out.println(EXIT + ". - Выйти из программы");
    }

    private boolean isItemExist(String id) {
        return this.tracker.findById(id) != null;
    }

    /**
     * Запускт программы.
     * @param args
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}
