package ru.job4j.foodstorage;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.foodstorage.food.Cheese;
import ru.job4j.foodstorage.food.Milk;
import ru.job4j.foodstorage.storages.*;

import java.util.Date;

public class StorageFactoryTest {

    StorageFactory storageFactory = new StorageFactory();

    @Test
    public void whenGetTrashStorage() {
        Milk houseIsTheVillage = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - 86400 * 10),
                new Date(System.currentTimeMillis() - 86400 * 5),
                100d,
                0d
        );
        IStorage storage = storageFactory.getStorage(houseIsTheVillage);
        Assert.assertTrue(storage instanceof Trash);
    }

    @Test
    public void whenGetWareHouseStorage() {
        Milk houseIsTheVillage = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - 86400 * 2),
                new Date(System.currentTimeMillis() + 86400 * 8),
                100d,
                0d
        );
        IStorage storage = storageFactory.getStorage(houseIsTheVillage);
        Assert.assertTrue(storage instanceof WareHouse);
    }

    @Test
    public void whenGetShopStorageAndCheckDiscountIs33dot33() {
        Milk houseIsTheVillage = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - 86400 * 8),
                new Date(System.currentTimeMillis() + 86400 * 2),
                100d,
                0d
        );
        IStorage storage = storageFactory.getStorage(houseIsTheVillage);
        Assert.assertTrue(storage instanceof Shop);
        Assert.assertThat(houseIsTheVillage.getDiscount(), Is.is(33.33d));
    }


    @Test
    public void whenGetShopStorageAndWithoutDiscount() {
        Cheese gauda = new Cheese(
                "Гауда",
                new Date(System.currentTimeMillis() - 86400 * 5),
                new Date(System.currentTimeMillis() + 86400 * 5),
                150.75d,
                0d
        );
        IStorage storage = storageFactory.getStorage(gauda);
        Assert.assertTrue(storage instanceof Shop);
        Assert.assertThat(gauda.getDiscount(), Is.is(0d));
    }
}