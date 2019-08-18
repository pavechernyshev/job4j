package ru.job4j.calculator;

import java.util.HashMap;

public class InteractCalc {

    public static void main(String[] args) {
        InteractCalc calc = new InteractCalc();
        calc.addAction(calc.new Div(), "/");
        calc.addAction(calc.new Add(), "+");
        calc.addAction(calc.new Multiple(), "*");
        calc.addAction(calc.new Subtract(), "-");
        calc.init(new ConsoleInput());
        calc.start();
    }

    private final Calculator calc = new Calculator();
    protected HashMap<String, Action> actions = new HashMap<>();
    private String menu = "";
    private String ln = System.lineSeparator();
    private Input userInput;
    private double lastRes = 0.0;
    private Action lastAction = null;

    public void init(Input userInput) {
        this.userInput = userInput;
        menuInit();
    }

    public void start() {
        showMenu();
        String userAnswer = userInput.askAction("Введите операцию:");
        while (!userAnswer.equals("exit")) {
            Action action;
            if (userAnswer.equals("repeat") && lastAction != null) {
                action = lastAction;
            } else {
                action = actions.get(userAnswer);
            }
            if (action != null) {
                double res = action.execute(this.userInput);
                saveLastRes(res);
                saveLastAction(action);
                showRes();
            }
            userAnswer = userInput.askAction("Введите операцию");
        }
    }

    private void saveLastAction(Action action) {
        lastAction = action;
    }

    private void saveLastRes(double res) {
        lastRes = res;
    }

    private void showRes() {
        System.out.println(lastRes);
    }

    private void showMenu() {
        System.out.println(menu);
    }

    public void addAction(Action action, String operand) {
        actions.put(operand, action);
    }

    private void menuInit() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Calculator actions:").append(ln);
        for (String key : actions.keySet()) {
            stringBuilder.append(key).append(ln);
        }
        stringBuilder.append("For repeat last action enter 'repeat'");
        stringBuilder.append("For exit enter 'exit'");
        menu = stringBuilder.toString();
    }

    public interface Action {
        double execute(Input userInput);
    }

    public class Div implements Action {
        @Override
        public double execute(Input userInput) {
            double first = userInput.askDigit("first num");
            double second = userInput.askDigit("second num");
            calc.div(first, second);
            return calc.getResult();
        }
    }

    public class Add implements Action {
        @Override
        public double execute(Input userInput) {
            double first = userInput.askDigit("first num");
            double second = userInput.askDigit("second num");
            calc.add(first, second);
            return calc.getResult();
        }
    }

    public class Multiple implements Action {
        @Override
        public double execute(Input userInput) {
            double first = userInput.askDigit("first num");
            double second = userInput.askDigit("second num");
            calc.multiple(first, second);
            return calc.getResult();
        }
    }

    public class Subtract implements Action {
        @Override
        public double execute(Input userInput) {
            double first = userInput.askDigit("first num");
            double second = userInput.askDigit("second num");
            calc.subtract(first, second);
            return calc.getResult();
        }
    }


}
