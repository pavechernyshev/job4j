package ru.job4j.foodstorage.storages;

import ru.job4j.foodstorage.food.IFood;

public class ColdPlaceStorageDecorator extends StorageDecorator {

    public ColdPlaceStorageDecorator(IStorage storageDecorator) {
        super(storageDecorator);
    }

    @Override
    public void put(IFood food) {
        // имитируем специаальные условия хранения
        this.getFoodList().add(0, food);
    }

    @Override
    public boolean accept(IFood food) {
        return super.accept(food) && food.isVegetable();
    }
}
