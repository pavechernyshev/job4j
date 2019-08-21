package ru.job4j.foodstorage;

import org.junit.Test;
import ru.job4j.foodstorage.control.ControlQuality;
import ru.job4j.foodstorage.food.Cheese;
import ru.job4j.foodstorage.food.Milk;
import ru.job4j.foodstorage.storages.*;

import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ControlQualityTest {

    IStorageFactory storageFactory = new StorageFactory();
    ControlQuality controlQuality = new ControlQuality(storageFactory);

    @Test
    public void putFoodWareHouse() {
        Milk houseIsTheVillage = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - 86400 * 2),
                new Date(System.currentTimeMillis() + 86400 * 8),
                100d,
                0d
        );
        Milk houseIsTheVillage1 = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - 86400 * 2),
                new Date(System.currentTimeMillis() + 86400 * 8),
                100d,
                0d
        );
        controlQuality.putFood(houseIsTheVillage);
        controlQuality.putFood(houseIsTheVillage1);
        HashMap<String, IStorage> hashMap = controlQuality.getStorages();
        assertEquals(1, hashMap.size());
        for (IStorage storage: hashMap.values()) {
            assertEquals(2, storage.getFoodList().size());
            assertTrue(storage instanceof WareHouse);
        }
    }

    @Test
    public void whenPutTrashFood() {
        Milk houseIsTheVillage = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - 86400 * 10),
                new Date(System.currentTimeMillis() - 86400 * 5),
                100d,
                0d
        );
        controlQuality.putFood(houseIsTheVillage);
        HashMap<String, IStorage> hashMap = controlQuality.getStorages();
        assertEquals(1, hashMap.size());
        for (IStorage storage: hashMap.values()) {
            assertEquals(1, storage.getFoodList().size());
            assertTrue(storage instanceof Trash);
        }
    }

    @Test
    public void whenPutShopFood() {
        Cheese gauda = new Cheese(
                "Гауда",
                new Date(System.currentTimeMillis() - 86400 * 5),
                new Date(System.currentTimeMillis() + 86400 * 5),
                150.75d,
                0d
        );
        Milk houseIsTheVillage = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - 86400 * 8),
                new Date(System.currentTimeMillis() + 86400 * 2),
                100d,
                0d
        );
        controlQuality.putFood(gauda);
        controlQuality.putFood(houseIsTheVillage);
        HashMap<String, IStorage> hashMap = controlQuality.getStorages();
        assertEquals(1, hashMap.size());
        for (IStorage storage: hashMap.values()) {
            assertEquals(2, storage.getFoodList().size());
            assertTrue(storage instanceof Shop);
        }
    }
}