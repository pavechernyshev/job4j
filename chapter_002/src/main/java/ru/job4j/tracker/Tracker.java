package ru.job4j.tracker;

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
    private final Item[] items = new Item[100];

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
        if (this.position < this.items.length) {
            this.items[this.position++] = item;
        }
        return item;
    }

    public void replace(String id, Item item) {
        for (int index = 0; index < this.items.length; index++) {
            if (this.items[index].getId().equals(id)) {
                this.items[index] = item;
                break;
            }
        }
    }

    public void delete(String id) {
        int count = 0;
        for (Item item: this.items) {
            if (this.items[count] != null && item.getId().equals(id)) {
                this.items[count] = null;
                break;
            }
            count++;
        }
        for (int index = count; index < this.items.length - 1; index++) {
            this.items[index] = this.items[index + 1];
        }
        //this.items = Arrays.copyOf(this.items, this.items.length - 1);
    }

    public Item[] findAll() {
        int count = 0;
        for (Item item: this.items) {
            if (item != null) {
                count++;
            }
        }
        Item[] res = new Item[count];
        count = 0;
        for (Item item: this.items) {
            if (item != null) {
                res[count++] = item;
            }
        }
        return res;
    }

    public Item[] findByName(String key) {
        Item[] res = new Item[this.items.length];
        int count = 0;
        for (Item item: this.items) {
            if (item != null && item.getName().equals(key)) {
                res[count++] = item;
            }
        }
        return Arrays.copyOf(res, count);
    }

    public Item findById(String id) {
        Item item = null;
        for (int index = 0; index < this.items.length; index++) {
            if (this.items[index] != null && this.items[index].getId() != null && this.items[index].getId().equals(id)) {
                item = this.items[index];
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
