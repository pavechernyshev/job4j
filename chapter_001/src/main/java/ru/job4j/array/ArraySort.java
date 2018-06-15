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
            boolean isCanOfIncrementFirst = false;
            boolean isCanOfIncrementSecond = false;

            if (indexOfFirst < sortArrayFirst.length) {
                isCanOfIncrementFirst = true;
            }

            if (indexOfSecond < sortArraySecond.length) {
                isCanOfIncrementSecond = true;
            }

            if (isCanOfIncrementFirst && isCanOfIncrementSecond) {
                if (sortArrayFirst[indexOfFirst] > sortArraySecond[indexOfSecond]) {
                    res[i] = sortArraySecond[indexOfSecond];
                    indexOfSecond++;
                } else {
                    res[i] = sortArrayFirst[indexOfFirst];
                    indexOfFirst++;
                }
            } else if (isCanOfIncrementFirst) {
                res[i] = sortArrayFirst[indexOfFirst];
                indexOfFirst++;
            } else if (isCanOfIncrementSecond) {
                res[i] = sortArraySecond[indexOfSecond];
                indexOfSecond++;
            }
        }


        return res;
    }
}
