package ru.job4j.foodstorage;

public class ColdPlaceStorageDecorator extends StorageDecorator {

    public ColdPlaceStorageDecorator(IStorage storageDecorator) {
        super(storageDecorator);
    }

    @Override
    public void put(IFood food) {
        // имитируем специаальные условия хранения
        this.getFoodList().add(0, food);
    }
}
