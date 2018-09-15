package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @version $Id$
 * @since 0.1
 */
public class Tracker {
    /**
     * Массив для хранение заявок.
     */
    private List<Item> items = new ArrayList<>();

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
        final boolean[] res = {false};
        this.items.forEach(i -> {
            if (i.getId().equals(id)) {
                item.setId(id);
                items.set(items.indexOf(i), item);
                res[0] = true;
            }
        });
        return res[0];
    }

    /**
     * Присваивет найденному элементу null и отправляет в конец массива.
     *
     * @param id идентификатор удаляемого элемента
     * @return успешное удаление.
     */
    public boolean delete(String id) {
        boolean res = false;
        if (id.length() > 0) {
            res = this.items.removeIf(item -> item.getId().equals(id));
        }
        if (res) {
            this.position--;
        }
        return res;
    }

    /**
     *
     * @return все заявки.
     */
    public List<Item> findAll() {
        return this.items;
    }

    /**
     * Ищет заявки по названию.
     *
     * @param key имя заявки.
     * @return найденные заявки.
     */
    public List<Item> findByName(String key) {
        final ArrayList<Item> res = new ArrayList<>();
        this.items.forEach(item -> {
            if (item.getName().equals(key)) {
                res.add(item);
            }
        });
        return res;
    }

    /**
     * Осуществляет поиск по заявкам по идентификатору.
     *
     * @param id уникальный идентификатор заявки.
     * @return заявку или null, если не найдено.
     */
    public Item findById(String id) {
        final Item[] item = {null};
        this.items.forEach(i -> {
            if (i != null && i.getId().equals(id)) {
                item[0] = i;
            }
        });
        return item[0];
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
