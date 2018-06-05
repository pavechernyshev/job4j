package ru.job4j.max;

/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Max {

    /**
     * Получаем мечисамольное из двух число.
     *
     * @param first
     * @param second
     * @return
     */
    public int max(int first, int second) {
        return first > second ? first : second;
    }

    /**
     * Получаем максимальное, из терх число.
     *
     * @param first
     * @param second
     * @param third
     * @return
     */
    public int max(int first, int second, int third) {
        int temp = this.max(first, second);
        return this.max(temp, third);
    }
}
