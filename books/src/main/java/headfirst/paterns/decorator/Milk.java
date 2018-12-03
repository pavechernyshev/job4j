package headfirst.paterns.decorator;

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
        return beverage.description + ", Mocha";
    }

    @Override
    public double cost() {
        return .20 + beverage.cost();
    }
}
