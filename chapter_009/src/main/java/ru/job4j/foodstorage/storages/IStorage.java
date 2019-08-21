package ru.job4j.foodstorage.storages;

import ru.job4j.foodstorage.food.IFood;

import java.util.List;

public interface IStorage {
    String getCode();
    void put(IFood food);
    boolean accept(IFood food);
    List<IFood> getFoodList();
}
