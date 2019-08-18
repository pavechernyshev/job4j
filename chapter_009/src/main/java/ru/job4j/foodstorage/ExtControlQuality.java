package ru.job4j.foodstorage;


public class ExtControlQuality extends ControlQuality {
    private final IStorageFactory storageFactory;

    ExtControlQuality(IStorageFactory storageFactory) {
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
