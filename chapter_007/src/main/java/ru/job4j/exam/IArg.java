package ru.job4j.exam;

public interface IArg {
     String getKey();
    void setValue(String value);
    String getValue();
    void setValidator(IArgValidator validator);
    boolean isValid();
    boolean isRequire();
    String help();
}
