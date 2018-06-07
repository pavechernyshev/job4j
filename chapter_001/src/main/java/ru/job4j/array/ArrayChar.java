package ru.job4j.array;

/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class ArrayChar {
    private char[] data;

    public ArrayChar(String line) {
        this.data = line.toCharArray();
    }

    /**
     * Проверяет. что слово начинается с префикса.
     * @param prefix префикс.
     * @return если слово начинаеться с префикса
     */
    public boolean startWith(String prefix) {
        boolean result = true;
        char[] value = prefix.toCharArray();
        if (value.length <= this.data.length) {
            for (int index = 0; index < value.length; index++) {
                if (this.data[index] != value[index]) {
                    result = false;
                    break;
                }
            }
        } else {
          result = false;
        }
        return result;
    }
}
