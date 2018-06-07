package ru.job4j.array;
/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class BubbleSort {
    public int[] sort(int[] array) {
        for (int counter = array.length - 1; counter >= 0; counter--) {
            for (int index = 0; index < counter; index++) {
                if (array[index] > array[index + 1]) {
                    int temp = array[index];
                    array[index] = array[index + 1];
                    array[index + 1] = temp;
                }
            }
        }
        return array;
    }
}
