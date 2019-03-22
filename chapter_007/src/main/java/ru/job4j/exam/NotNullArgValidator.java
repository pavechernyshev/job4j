package ru.job4j.exam;

public class NotNullArgValidator implements IArgValidator {

    @Override
    public boolean validate(String value) {
        boolean res = false;
        if (value != null && !value.equals("")) {
            res = true;
        }
        return res;
    }
}
