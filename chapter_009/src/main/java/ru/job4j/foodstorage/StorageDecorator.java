package ru.job4j.foodstorage;

import java.util.List;

public abstract class StorageDecorator implements IStorage {
    protected IStorage storageDecorator;

    public StorageDecorator(IStorage storageDecorator) {
        this.storageDecorator = storageDecorator;
    }

    @Override
    public String getCode() {
        return storageDecorator.getCode();
    }

    @Override
    public void put(IFood food) {
        storageDecorator.put(food);
    }

    @Override
    public List<IFood> getFoodList() {
        return storageDecorator.getFoodList();
    }
}
