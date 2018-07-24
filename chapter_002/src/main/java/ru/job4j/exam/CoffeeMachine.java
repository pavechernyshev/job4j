package ru.job4j.exam;

/**
 *
 * @author Pavel Chernyshev (titan100695@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class CoffeeMachine {

    //Номиналы монет от большей к меньшей
    private final int[] nominals = {10, 5, 2, 1};
    private int[] monets = new int[this.nominals.length];

    public int[] changes(int value, int price) throws NotEnoughMoneyException {
        if (value < price) {
            throw new NotEnoughMoneyException("Не достаточно средств!");
        }
        int index = 0;
        int change = value - price;
        int countMonets = 0;
        for (int nominal: this.nominals) {
            if (change >= nominal) {
                this.monets[index] = change / nominal;
                countMonets += this.monets[index];
                change = change % nominal;
                if (change == 0) {
                    break;
                }
            }
            index++;
        }
        int[] res = new int[countMonets];
        int position = 0;
        for (int i = 0; i < this.nominals.length; i++) {
            if (this.monets[i] > 0) {
                for (int startPos = position; position < startPos + this.monets[i]; position++) {
                    res[position] = this.nominals[i];
                }
            }
        }
        return res;
    }
}
