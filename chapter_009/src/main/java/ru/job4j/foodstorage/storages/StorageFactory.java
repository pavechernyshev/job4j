package ru.job4j.foodstorage.storages;

import ru.job4j.foodstorage.food.FoodInspector;
import ru.job4j.foodstorage.food.IFood;

public class StorageFactory implements IStorageFactory {
    FoodInspector foodInspector = new FoodInspector();

    @Override
    public IStorage getStorage(IFood food) {
        IStorage storage;
        int expiredPercent = foodInspector.getExpirePercent(food);
        if (expiredPercent == 100) {
            storage = new Trash("North spb");
        } else if (expiredPercent > 75) {
            food.setDiscount(33.33d);
            storage = new Shop("Okey");
        } else if (expiredPercent >= 25) {
            storage = new Shop("Okey");
        } else {
            storage = new WareHouse("Stock spb");
        }
        return storage;
    }
}
