package headfirst.patterns.decorator.starbuzz;

public class Soy extends CondimentDecorator {

    private Beverage beverage;

    public Beverage getBeverage() {
        return beverage;
    }

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Soy";
    }

    @Override
    public double cost() {
        return .15 + beverage.cost();
    }
}
