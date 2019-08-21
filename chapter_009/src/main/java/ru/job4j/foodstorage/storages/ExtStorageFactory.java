package ru.job4j.foodstorage.storages;


import ru.job4j.foodstorage.food.FoodInspector;
import ru.job4j.foodstorage.food.IFood;
import ru.job4j.foodstorage.storages.*;

public class ExtStorageFactory implements IStorageFactory {
    FoodInspector foodInspector;
    WareHouse wareHouse;
    WareHouse wareHouse2;
    Recycle recycle;
    ColdPlaceStorageDecorator coldPlaceStorageDecorator;
    Trash trash;
    Shop shop;

    public ExtStorageFactory() {
        this.foodInspector  = new FoodInspector();
        this.wareHouse  = new WareHouse("Stock spb");
        this.wareHouse2  = new WareHouse("Stock spb 2");
        this.recycle  = new Recycle("center of recycling");
        this.coldPlaceStorageDecorator  = new ColdPlaceStorageDecorator(recycle);
        this.trash  = new Trash("North spb");
        this.shop  = new Shop("Okey");
    }

    @Override
    public IStorage getStorage(IFood food) {
        IStorage storage;
        int expiredPercent = foodInspector.getExpirePercent(food);
        if (expiredPercent == 100) {
            if (food.canReproduct() && food.isVegetable()) {
                storage = coldPlaceStorageDecorator;
            } else if (food.canReproduct()) {
                storage = recycle;
            } else {
                storage = trash;
            }
        } else if (expiredPercent > 75) {
            food.setDiscount(33.33d);
            storage = shop;
        } else if (expiredPercent >= 25) {
            storage = shop;
        } else {
            if (wareHouse.getFoodList().size() < 1) {
                storage = wareHouse;
            } else {
                storage = wareHouse2;
            }
        }
        return storage;
    }
}
