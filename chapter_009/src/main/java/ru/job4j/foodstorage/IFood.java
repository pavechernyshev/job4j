package ru.job4j.foodstorage;

import java.util.Date;

public interface IFood {
    String getName();
    Date getCreatedDate();
    Date getExpireDate();
    double getPrice();
    double getDiscount();
    void setName(String name);
    void setPrice(double prise);
    void setDiscount(double discount);
}
