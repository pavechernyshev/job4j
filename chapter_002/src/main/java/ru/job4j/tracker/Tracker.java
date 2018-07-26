package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

/**
 * @version $Id$
 * @since 0.1
 */
public class Tracker {
    /**
     * Массив для хранение заявок.
     */
    private final ArrayList<Item> items = new ArrayList<>();

    /**
     * Указатель ячейки для новой заявки.
     */
    private int position = 0;

    /**
     * Метод реализаущий добавление заявки в хранилище
     * @param item новая заявка
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        if (this.position <= this.items.size()) {
            this.items.add(this.position++, item);
        }
        return item;
    }

    /**
     * Метод перезаписывает заявку по идентификатору.
     *
     * @param id уникальный идентификатор заявки.
     * @param item новая заявка.
     * @return статус выполнения операции.
     */
    public boolean replace(String id, Item item) {
        boolean res = false;
        for (int index = 0; index < this.position; index++) {
            if (this.items.get(index) != null && this.items.get(index).getId().equals(id)) {
                this.items.set(index, item);
                item.setId(id);
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * Присваивет найденному элементу null и отправляет в конец массива.
     *
     * @param id
     * @return успешное удаление.
     */
    public boolean delete(String id) {
        int delete = -1;
        boolean res = false;
        for (int index = 0; index < this.position; index++) {
            if (this.items.get(index) != null && this.items.get(index).getId().equals(id)) {
                this.items.remove(index);
                res = true;
                this.position--;
                break;
            }
        }
        return res;
    }

    /**
     *
     * @return все заявки.
     */
    public Item[] findAll() {
        return this.items.toArray(new Item[position]);
    }

    /**
     * Ищет заявки по названию.
     *
     * @param key имя заявки.
     * @return найденные заявки.
     */
    public Item[] findByName(String key) {
        ArrayList<Item> res = new ArrayList<>();
        int count = 0;
        for (Item item: this.items) {
            if (item != null && item.getName().equals(key)) {
                res.add(count++, item);
            }
        }
        return res.toArray(new Item[res.size()]);
    }

    /**
     * Осуществляет поиск по заявкам по идентификатору.
     *
     * @param id уникальный идентификатор заявки.
     * @return заявку или null, если не найдено.
     */
    public Item findById(String id) {
        Item item = null;
        for (int index = 0; index < this.position; index++) {
            if (this.items.get(index) != null && this.items.get(index).getId().equals(id)) {
                item = this.items.get(index);
                break;
            }
        }
        return item;
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     * @return Уникальный ключ.
     */
    private String generateId() {
        Random random = new Random();
        return String.valueOf(System.currentTimeMillis()) + "_" + String.valueOf(random.nextInt(1000));
    }
}
