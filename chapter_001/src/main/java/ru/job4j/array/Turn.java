package ru.job4j.array;

/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Turn {
    public int[] turn(int[] array) {
        int countOfIteration = (int) Math.floor(array.length / 2);
        for (int index = 0; index < countOfIteration; index++) {
            int temp = array[index];
            array[index] = array[array.length - 1 - index];
            array[array.length - 1 - index] = temp;
        }
        return array;
    }
}
