package ru.job4j.loop;
/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Counter {

    /**
     * Подсчитываюет диапозон четных чисел.
     *
     * @param start
     * @param finish
     * @return
     */
    public int add(int start, int finish) {
        int temp = 0;
        for (int i = start; i <= finish; i++) {
            if (i % 2 == 0) {
                temp += i;
            }
        }

        return temp;
    }
}
