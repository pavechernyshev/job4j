package ru.job4j.calculator;

public class IngeneerCalc {
    public void start(Input input) {
        InteractCalc calc = new InteractCalc();
        calc.addAction(calc.new Div(), "/");
        calc.addAction(calc.new Add(), "+");
        calc.addAction(calc.new Multiple(), "*");
        calc.addAction(calc.new Subtract(), "-");
        calc.addAction(new Sqr(), "^");
        calc.init(new ConsoleInput());
        calc.start();
    }

    public class Sqr implements InteractCalc.Action {
        @Override
        public double execute(Input userInput) {
            double num = userInput.askDigit("num");
            return num * num;
        }
    }
}
