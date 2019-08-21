package ru.job4j.foodstorage;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.foodstorage.food.Cheese;
import ru.job4j.foodstorage.food.IFood;
import ru.job4j.foodstorage.food.Milk;
import ru.job4j.foodstorage.food.Potato;
import ru.job4j.foodstorage.storages.*;

import java.util.Date;

public class ExtStorageFactoryTest {

    ExtStorageFactory storageFactory = new ExtStorageFactory();

    @Test
    public void whenGetTrashStorage() {
        IFood houseIsTheVillage = new Milk(
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
    public void whenGetRecycleStorage() {
        Milk houseIsTheVillage = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - 86400 * 10),
                new Date(System.currentTimeMillis() - 86400 * 5),
                100d,
                0d
        );
        houseIsTheVillage.setCanReproduct(true);
        IStorage storage = storageFactory.getStorage(houseIsTheVillage);
        Assert.assertTrue(storage instanceof Recycle);
    }

    @Test
    public void whenAddExpiredVegetableThenColdPlaceStorageDecorator() {
        Potato potatoFree = new Potato(
                "КартофельФрии",
                new Date(System.currentTimeMillis() - 86400 * 10),
                new Date(System.currentTimeMillis() - 86400 * 6),
                200,
                0d
        );
        potatoFree.setCanReproduct(true);
        potatoFree.setIsVegetable(true);
        IStorage storage = storageFactory.getStorage(potatoFree);
        Assert.assertTrue(storage instanceof ColdPlaceStorageDecorator);
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

    @Test
    public void whenFirstWareHouseIsFullThenGetSecondWareHouse() {
        Milk houseIsTheVillage = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - 86400 * 2),
                new Date(System.currentTimeMillis() + 86400 * 8),
                100d,
                0d
        );
        IStorage storage = storageFactory.getStorage(houseIsTheVillage);
        storage.put(houseIsTheVillage);
        Assert.assertTrue(storage instanceof WareHouse);
        Assert.assertThat("Stock spb", Is.is(storage.getCode()));
        Milk houseIsTheVillage2 = new Milk(
                "Домик в деревне 2",
                new Date(System.currentTimeMillis() - 86400 * 2),
                new Date(System.currentTimeMillis() + 86400 * 8),
                100d,
                0d
        );
        storage = storageFactory.getStorage(houseIsTheVillage2);
        Assert.assertTrue(storage instanceof WareHouse);
        Assert.assertThat("Stock spb 2", Is.is(storage.getCode()));
    }
}