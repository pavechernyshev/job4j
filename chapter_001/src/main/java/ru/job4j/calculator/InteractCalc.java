package ru.job4j.calculator;

import java.util.HashMap;

public class InteractCalc {

    private final Calculator calc = new Calculator();
    private HashMap<String, Action> actions = new HashMap<>();
    private String menu = "";
    private String ln = System.lineSeparator();
    private final Input consoleInput = new ConsoleInput();
    private double lastRes = 0.0;
    private Action lastAction = null;

    public void init() {
        actionsInit();
        menuInit();
    }

    public void start() {
        showMenu();
        String userAnswer = consoleInput.askAction("Введите операцию:");
        while (!userAnswer.equals("exit")) {
            Action action;
            if (userAnswer.equals("repeat") && lastAction != null) {
                action = lastAction;
            } else {
                action = actions.get(userAnswer);
            }
            if (action != null) {
                double first = consoleInput.askDigit("Введите первое число");
                double second = consoleInput.askDigit("Введите второе число");
                double res = action.execute(first, second);
                saveLastRes(res);
                saveLastAction(action);
                showRes();
            }
            userAnswer = consoleInput.askAction("Введите операцию");
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

    private void actionsInit() {
        actions.put("/", new Div());
        actions.put("+", new Add());
        actions.put("*", new Multiple());
        actions.put("-", new Subtract());
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

    private interface Action {
        double execute(double first, double second);
    }

    private class Div implements Action {
        @Override
        public double execute(double first, double second) {
            calc.div(first, second);
            return calc.getResult();
        }
    }

    private class Add implements Action {
        @Override
        public double execute(double first, double second) {
            calc.add(first, second);
            return calc.getResult();
        }
    }

    private class Multiple implements Action {
        @Override
        public double execute(double first, double second) {
            calc.multiple(first, second);
            return calc.getResult();
        }
    }

    private class Subtract implements Action {
        @Override
        public double execute(double first, double second) {
            calc.subtract(first, second);
            return calc.getResult();
        }
    }


}
