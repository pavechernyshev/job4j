package ru.job4j.exam;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CoffeeMachineTest {
    @Test
    public void whenCoffeePriceFortyOneAndNominalOneHundredFiftyThen10x10And5x1and2x2() {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        int[] res = coffeeMachine.changes(150, 41);
        assertThat(res, is(new int[] {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 2, 2}));
    }

    @Test
    public void whenCoffeePriceFortyFourAndNominalOneHundredFiftyThen10x10And5x1and1x1() {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        int[] res = coffeeMachine.changes(150, 44);
        assertThat(res, is(new int[] {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 1}));
    }

    @Test
    public void whenCoffeeTwentyTwoAndNominalIsFiftyThen10x2And5x1and2x2and1x1() {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        int[] res = coffeeMachine.changes(50, 22);
        assertThat(res, is(new int[] {10, 10, 5, 2, 1}));
    }

    @Test
    public void whenCoffeePriceMoreMoneyThenException() {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        String expect = "Не достаточно средств!";
        String msg = "";
        try {
            int[] changes = coffeeMachine.changes(50, 60);
        } catch (NotEnoughMoneyException nem) {
            msg = nem.getMessage();
        }
        assertThat(msg, is(expect));
    }
}
