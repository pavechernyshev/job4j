package ru.job4j.foodstorage;

import java.util.Date;

public class Cheese extends Food {
    Cheese(String name, Date createDate, Date expireDate, double price, double discount) {
        super(name, createDate, expireDate, price, discount);
    }
}
