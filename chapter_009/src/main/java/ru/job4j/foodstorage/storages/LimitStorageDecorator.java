package ru.job4j.foodstorage.storages;

import ru.job4j.foodstorage.food.IFood;

public class LimitStorageDecorator extends StorageDecorator {
    private int limit = 1;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public LimitStorageDecorator(IStorage storageDecorator) {
        super(storageDecorator);
    }

    @Override
    public boolean accept(IFood food) {
        return super.accept(food) && this.getFoodList().size() < limit;
    }
}
