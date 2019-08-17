package ru.job4j.foodstorage;

public interface IStorageFactory {
    IStorage getStorage(IFood food);
}
