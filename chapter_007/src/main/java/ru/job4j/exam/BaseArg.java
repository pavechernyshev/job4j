package ru.job4j.exam;

public class BaseArg implements IArg {
    private String key;
    private String value = "";
    private IArgValidator validator;
    private boolean require;
    private String help;

    BaseArg(String key, IArgValidator validator, boolean require, String help) {
        this.key = key;
        this.setValidator(validator);
        this.require = require;
        this.help = help;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValidator(IArgValidator validator) {
        this.validator = validator;
    }

    @Override
    public boolean isValid() {
        return this.validator.validate(this.value);
    }

    @Override
    public boolean isRequire() {
        return this.require;
    }

    @Override
    public String help() {
        return this.help;
    }
}
