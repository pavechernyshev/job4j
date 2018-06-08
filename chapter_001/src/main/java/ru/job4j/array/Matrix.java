package ru.job4j.array;

/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Matrix {
    public int[][] multiple(int size) {
        int[][] matrix = new int[size][size];
        for (int columnIndex = 0; columnIndex < size; columnIndex++) {
            for (int lineIndex = 0; lineIndex < size; lineIndex++) {
                matrix[columnIndex][lineIndex] = (columnIndex + 1) * (lineIndex + 1);
            }
        }
        return matrix;
    }
}
