package ru.job4j.tracker;

import javax.jws.soap.SOAPBinding;

public class MenuTracker {

    private Input input;
    private Tracker tracker;
    private UserAction[] userActions = new UserAction[7];
    private boolean exit = false;

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public void select(int key) {
        for (UserAction action: this.userActions) {
            if (action != null && action.key() == key) {
                action.execute(this.input, this.tracker);
            }
        }
    }

    public int[] getRange() {
        int[] range = new int[this.userActions.length];
        int count = 0;
        for (UserAction action: this.userActions) {
            range[count++] = action.key();
        }
        return range;
    }

    public void fillActions() {
        this.userActions[0] = new AddItem();
        this.userActions[1] = new ShowItems();
        this.userActions[2] = new EditItem();
        this.userActions[3] = new DeleteItem();
        this.userActions[4] = new FindById();
        this.userActions[5] = new FindByName();
        this.userActions[6] = new Exit();
    }

    public void show() {
        printMenuHead();
        for (UserAction action: this.userActions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    public boolean isExit() {
        return exit;
    }

    private void setExit(boolean exit) {
        this.exit = exit;
    }

    private void printMenuHead() {
        System.out.println("Меню.");
    }

    private void printNotItemFound() {
        System.out.println("------------ Ну удалось найти заявку --------------");
    }

    private class AddItem implements UserAction {
        @Override
        public int key() {
            return 0;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Добавление новой заявки --------------");
            String name = input.ask("Введите имя заявки :");
            String desc = input.ask("Введите описание заявки :");
            Item item = new Item(name, desc);
            tracker.add(item);
            System.out.println("------------ Новая заявка с getId : " + item.getId() + "-----------");
        }

        @Override
        public String info() {
            return String.format("%s. - %s", this.key(), "Добавить новую заявку");
        }
    }

    private class ShowItems implements UserAction {
        @Override
        public int key() {
            return 1;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Отображение всех заявок --------------");
            Item[] items = tracker.findAll();
            for (Item item: items) {
                System.out.println(item.toString());
            }
            if (items.length == 0) {
                System.out.println("------------ Заявок нет. --------------");
            }
        }

        @Override
        public String info() {
            return String.format("%s. - %s", this.key(), "Показать заявки");
        }
    }

    private class EditItem implements UserAction {
        @Override
        public int key() {
            return 2;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Изменение заявки --------------");
            String id = input.ask("Введите идентификатор заявки :");
            String name = input.ask("Введите имя заявки :");
            String desc = input.ask("Введите описание заявки :");
            Item item = new Item(name, desc);
            if (tracker.replace(id, item)) {
                System.out.println("------------ Завка изменена --------------");
            } else {
                printNotItemFound();
            }
        }

        @Override
        public String info() {
            return String.format("%s. - %s", this.key(), "Редактировать заявку");
        }
    }

    private class DeleteItem implements UserAction {
        @Override
        public int key() {
            return 3;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Удаление заявки --------------");
            String id = input.ask("Введите идентификатор заявки :");
            if (tracker.delete(id)) {
                System.out.println("------------ Заявка удалена --------------");
            } else {
                printNotItemFound();
            }
        }

        @Override
        public String info() {
            return String.format("%s. - %s", this.key(), "Удалить заявку");
        }
    }

    private class FindById implements UserAction {
        @Override
        public int key() {
            return 4;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Поиск заявки по идентификатору --------------");
            String id = input.ask("Введите идентификатор заявки :");
            Item item = tracker.findById(id);
            if (item != null) {
                System.out.println(item.toString());
            } else {
                printNotItemFound();
            }
        }

        @Override
        public String info() {
            return String.format("%s. - %s", this.key(), "Найти заявку по идентификатору");
        }
    }

    private class FindByName implements UserAction {
        @Override
        public int key() {
            return 5;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Поиск заявки по наименованию --------------");
            String name = input.ask("Введите имя заявки :");
            Item[] items = tracker.findByName(name);
            for (Item item: items) {
                System.out.println(item.toString());
            }
            if (items.length == 0) {
                printNotItemFound();
            }
        }

        @Override
        public String info() {
            return String.format("%s. - %s", this.key(), "Найти заявку по названию");
        }
    }

    private class Exit implements  UserAction {
        @Override
        public int key() {
            return 6;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            setExit(true);
        }

        @Override
        public String info() {
            return String.format("%s. - %s", this.key(), "Выйти из программы");
        }
    }
}
