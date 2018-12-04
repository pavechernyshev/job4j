package headfirst.patterns.decorator.starbuzz;

public class Milk extends CondimentDecorator {

    private Beverage beverage;

    public Beverage getBeverage() {
        return beverage;
    }

    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }

    @Override
    public double cost() {
        return .10 + beverage.cost();
    }
}
