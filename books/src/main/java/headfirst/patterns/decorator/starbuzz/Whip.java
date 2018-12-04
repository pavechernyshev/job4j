package headfirst.patterns.decorator.starbuzz;

public class Whip extends CondimentDecorator {

    private Beverage beverage;

    public Beverage getBeverage() {
        return beverage;
    }

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whip";
    }

    @Override
    public double cost() {
        return .10 + beverage.cost();
    }
}
