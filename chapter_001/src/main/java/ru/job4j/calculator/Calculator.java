package ru.job4j.calculator;

/**
    Calculator.

    * @author Pavel Chernyshev
    * @since 02.06.2018
    * @version 0.1
 */
public class Calculator {

    /**
     * property result contains arithmetic result.
     */
    private double result;

    /**
     * method addition.
     *
     * @param first
     * @param second
     */
    public void add(double first, double second) {
        this.result = first + second;
    }

    /**
     * method subtraction.
     *
     * @param first
     * @param second
     */
    public void subtract(double first, double second) {
        this.result = first - second;
    }

    /**
     * method division.
     *
     * @param first
     * @param second
     */
    public void div(double first, double second) {
        if (second != 0) {
            this.result = first / second;
        }
    }

    /**
     * method multiplication.
     *
     * @param first
     * @param second
     */
    public void multiple(double first, double second) {
        this.result = first * second;
    }

    /**
     * get result of operations.
     *
     * @return
     */
    public double getResult() {
        return this.result;
    }
}
