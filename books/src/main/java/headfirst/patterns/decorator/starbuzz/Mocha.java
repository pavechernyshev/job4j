package headfirst.patterns.decorator.starbuzz;

public class Mocha extends CondimentDecorator {

    private Beverage beverage;

    public Beverage getBeverage() {
        return beverage;
    }

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Mocha";
    }

    @Override
    public double cost() {
        return .20 + beverage.cost();
    }
}
