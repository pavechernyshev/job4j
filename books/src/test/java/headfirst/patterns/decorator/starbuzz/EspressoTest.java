package headfirst.patterns.decorator.starbuzz;

import org.hamcrest.core.Is;
import org.junit.Test;
import static org.junit.Assert.*;

public class EspressoTest {

    @Test
    public void whenTestEspresso() {
        Beverage beverage = new Espresso();
        assertThat(beverage.getDescription(), Is.is("Espresso"));
        assertThat(beverage.cost(), Is.is(1.99));
        beverage = new Mocha(beverage);
        assertThat(beverage.cost(), Is.is(2.19));
        assertThat(beverage.getDescription(), Is.is("Espresso, Mocha"));
        beverage = new Mocha(beverage);
        assertThat(beverage.cost(), Is.is(2.39));
        assertThat(beverage.getDescription(), Is.is("Espresso, Mocha, Mocha"));
        beverage = new Whip(beverage);
        assertThat(beverage.cost(), Is.is(2.49));
        assertThat(beverage.getDescription(), Is.is("Espresso, Mocha, Mocha, Whip"));

    }

}