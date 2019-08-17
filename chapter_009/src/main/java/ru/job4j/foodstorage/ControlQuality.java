package ru.job4j.foodstorage;

import java.util.HashMap;

public class ControlQuality {
    private final IStorageFactory storageFactory;
    private final HashMap<String, IStorage> storageHashMap = new HashMap<>();

    ControlQuality(IStorageFactory storageFactory) {
        this.storageFactory = storageFactory;
    }

    public void putFood(IFood food) {
        IStorage storage = this.storageFactory.getStorage(food);
        if (storageHashMap.containsKey(storage.getCode())) {
            storage = storageHashMap.get(storage.getCode());
        }
        storage.put(food);
        storageHashMap.putIfAbsent(storage.getCode(), storage);
    }

    public HashMap<String, IStorage> getStorages() {
        return storageHashMap;
    }
}
