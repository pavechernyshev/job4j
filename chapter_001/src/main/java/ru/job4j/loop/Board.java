package ru.job4j.loop;

/**
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Board {
    public String paint(int width, int height) {
        StringBuilder screen = new StringBuilder();
        String ln = System.lineSeparator();
        for (int lineNum = 0; lineNum < height; lineNum++) {
            for (int colNum = 0; colNum < width; colNum++) {
                // условие проверки, что писать пробел или X
                // Выше в задании мы определили закономерность, когда нужно проставлять X
                if ((lineNum + colNum) % 2 == 0) {
                    screen.append("X");
                } else {
                    screen.append(" ");
                }
            }
            // добавляем перевод на новую строку.
            screen.append(ln);
        }
        return screen.toString();
    }
}
