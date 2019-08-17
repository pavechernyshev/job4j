package ru.job4j.foodstorage;

import java.util.List;

public interface IStorage {
    String getCode();
    void put(IFood food);
    List<IFood> getFoodList();
}
