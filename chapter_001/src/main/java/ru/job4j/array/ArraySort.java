package ru.job4j.array;

/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class ArraySort {

    public int[] mergeAndSort(int[] sortArrayFirst, int[] sortArraySecond) {
        int lengthOfBothArrays = sortArrayFirst.length + sortArraySecond.length;
        int[] res = new int[lengthOfBothArrays];
        int indexOfFirst = 0;
        int indexOfSecond = 0;
        for (int i = 0; i < lengthOfBothArrays; i++) {
            if (indexOfFirst < sortArrayFirst.length && indexOfSecond < sortArraySecond.length) {
                if (sortArrayFirst[indexOfFirst] > sortArraySecond[indexOfSecond]) {
                    res[i] = sortArraySecond[indexOfSecond];
                    indexOfSecond++;
                } else {
                    res[i] = sortArrayFirst[indexOfFirst];
                    indexOfFirst++;
                }

            } else if (indexOfFirst < sortArrayFirst.length) {
                res[i] = sortArrayFirst[indexOfFirst];
                indexOfFirst++;
            } else if (indexOfSecond < sortArraySecond.length) {
                res[i] = sortArraySecond[indexOfSecond];
                indexOfSecond++;
            }

        }
        return res;
    }
}
