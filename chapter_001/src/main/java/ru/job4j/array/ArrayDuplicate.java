package ru.job4j.array;

import java.util.Arrays;
/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class ArrayDuplicate {

    /**
     * Удаляем дубликаты сохраняя последовательность в массиве.
     *
     * @param array
     * @return
     */
    public String[] remove(String[] array) {
        int unique = array.length;
        for (int out = 0; out < unique - 1; out++) {
            for (int in = out + 1; in < unique; in++) {
                if (array[out] == array[in]) {
                    for (int indexOfCopyElement = in; indexOfCopyElement < unique - 1; indexOfCopyElement++) {
                        array[indexOfCopyElement] = array[indexOfCopyElement + 1];
                    }
                    unique--;
                    in--;
                }
            }
        }
        return Arrays.copyOf(array, unique);
    }
}
