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
        setMonets(this.nominals, value - price);
        int[] res = new int[getCountMonets()];
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

    private int getCountMonets() {
        int count = 0;
        if (this.monets.length > 0) {
            for (int monets : this.monets) {
                count += monets;
            }
        }
        return count;
    }

    private void setMonets(int[] nominals, int change) {
        int index = 0;
        for (int nominal: nominals) {
            if (change >= nominal) {
                this.monets[index] = change / nominal;
                change = change % nominal;
                if (change == 0) {
                    break;
                }
            }
            index++;
        }
    }
}
