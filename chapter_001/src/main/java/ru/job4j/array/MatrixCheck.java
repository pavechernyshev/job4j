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
}
