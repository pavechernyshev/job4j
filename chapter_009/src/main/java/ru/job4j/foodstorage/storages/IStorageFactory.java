package ru.job4j.foodstorage.storages;

import ru.job4j.foodstorage.food.IFood;
import ru.job4j.foodstorage.storages.IStorage;

public interface IStorageFactory {
    IStorage getStorage(IFood food);
}
