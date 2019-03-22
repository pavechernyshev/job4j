package ru.job4j.exam;

public class NullArgValidator implements IArgValidator {
    @Override
    public boolean validate(String value) {
        boolean res = true;
        if (value != null && !value.equals("")) {
            res = false;
        }
        return res;
    }
}
