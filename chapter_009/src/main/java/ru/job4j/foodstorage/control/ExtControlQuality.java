package ru.job4j.foodstorage.control;


import ru.job4j.foodstorage.control.ControlQuality;
import ru.job4j.foodstorage.food.IFood;
import ru.job4j.foodstorage.storages.IStorage;
import ru.job4j.foodstorage.storages.IStorageFactory;

public class ExtControlQuality extends ControlQuality {
    private final IStorageFactory storageFactory;

    public ExtControlQuality(IStorageFactory storageFactory) {
        super(storageFactory);
        this.storageFactory = storageFactory;
    }

    @Override
    public void putFood(IFood food) {
        IStorage storage = this.storageFactory.getStorage(food);
        storage.put(food);
        super.getStorages().putIfAbsent(storage.getCode(), storage);
    }

}
