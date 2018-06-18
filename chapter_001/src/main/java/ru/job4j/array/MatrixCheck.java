package ru.job4j.array;

/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class MatrixCheck {
    public boolean mono(boolean[][] data) {
        boolean result = true;
        for (int index = 0; index < data.length; index++) {
            if (data[index][index] != data[0][0] || data[index][data.length - 1 - index] != data[0][0]) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Проверяет заполнена ли хотя-бы одна диагональ значениями check.
     *
     * @param data
     * @param check
     * @return
     */
    public boolean hasFillOneDiagonal(boolean[][] data, boolean check) {
        boolean res = true;
        boolean isDiagonal = true;
        boolean isDiagonalReverse = true;
        for (int index = 0; index < data.length; index++) {
            if (data[index][index] != check) {
                isDiagonal = false;
            }
            if (data[index][data.length - 1 - index] != check) {
                isDiagonalReverse = false;
            }
            if (!isDiagonal && !isDiagonalReverse) {
                res = false;
                break;
            }
        }
        return res;
    }

    public boolean hasFillOneHorizontal(boolean[][] data, boolean check) {
        boolean res = false;
        for (boolean[] row: data) {
            boolean isMonoRow = true;
            for (boolean item: row) {
                if (item != check) {
                    isMonoRow = false;
                    break;
                }
            }
            if (isMonoRow) {
                res = true;
                break;
            }
        }
        return res;
    }

    public boolean hasFillOneVertical(boolean[][] data, boolean check) {
        boolean res = false;
        int rowLength = data[0].length;
        for (int columnIndex = 0; columnIndex < rowLength; columnIndex++) {
            boolean isMonoCol = true;
            for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
                if (data[rowIndex][columnIndex] != check) {
                    isMonoCol = false;
                }
            }
            if (isMonoCol) {
                res = true;
                break;
            }
        }
        return res;
    }

    public boolean hasFillOneHorizontalOrVertical(boolean[][] data, boolean check) {
        boolean res = false;
        MatrixCheck matrix = new MatrixCheck();
        if (matrix.hasFillOneHorizontal(data, check)) {
            res = true;
        } else {
            if (matrix.hasFillOneVertical(data, check)) {
                res = true;
            }
        }
        return res;
    }
}
