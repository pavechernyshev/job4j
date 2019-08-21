package ru.job4j.foodstorage.control;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.foodstorage.food.*;
import ru.job4j.foodstorage.storages.*;
import java.util.Date;

import static org.junit.Assert.*;

public class StorageQualityTest {
    StorageQuality storageQuality = new StorageQuality();

    @Before
    public void start() {
        IStorage recycle = new Recycle("center of recycling");
        storageQuality.addStorage(new LimitStorageDecorator(new WareHouse("Stock spb")), 10);
        storageQuality.addStorage(new WareHouse("Stock spb 2"), 20);
        storageQuality.addStorage(new Shop("Okey"), 30);
        storageQuality.addStorage(new ColdPlaceStorageDecorator(recycle), 40);
        storageQuality.addStorage(recycle, 50);
        storageQuality.addStorage(new Trash("North spb"), 60);
    }

    @Test
    public void accept() {

        Milk houseIsTheVillage = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - 86400 * 10),
                new Date(System.currentTimeMillis() - 86400 * 5),
                100d,
                0d
        );
        houseIsTheVillage.setCanReproduct(true);
        Potato potatoFree = new Potato(
                "Картофель Фрии",
                new Date(System.currentTimeMillis() - 86400 * 11),
                new Date(System.currentTimeMillis() - 86400 * 6),
                200,
                0d
        );
        potatoFree.setCanReproduct(true);
        potatoFree.setIsVegetable(true);
        IStorage storage = storageQuality.accept(houseIsTheVillage);
        assertTrue(storage instanceof Recycle);
        storage = storageQuality.accept(potatoFree);
        assertTrue(storage instanceof ColdPlaceStorageDecorator);
        //картофель добавлялся вторым, но по логике должен встать на первое место
        assertThat("Картофель Фрии", Is.is(storage.getFoodList().get(0).getName()));
        assertThat("Домик в деревне", Is.is(storage.getFoodList().get(1).getName()));
        assertThat(storage.getFoodList().size(), Is.is(2));
    }

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
        Milk houseIsTheVillage2 = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - 86400 * 2),
                new Date(System.currentTimeMillis() + 86400 * 8),
                100d,
                0d
        );
        IStorage storage = storageQuality.accept(houseIsTheVillage);
        assertTrue(storage instanceof LimitStorageDecorator);
        IStorage storage2 = storageQuality.accept(houseIsTheVillage1);
        assertTrue(storage2 instanceof WareHouse);
        storage2 = storageQuality.accept(houseIsTheVillage2);
        assertTrue(storage2 instanceof WareHouse);
        //здесь имитируется переполнение склада и распределение продуктов во второй склад
        assertThat(storage.getFoodList().size(), Is.is(1));
        assertThat(storage2.getFoodList().size(), Is.is(2));
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
        IStorage storage = storageQuality.accept(houseIsTheVillage);
        assertTrue(storage instanceof Trash);
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
        IStorage storage = storageQuality.accept(gauda);
        assertTrue(storage instanceof Shop);
        storage = storageQuality.accept(houseIsTheVillage);
        assertTrue(storage instanceof Shop);
        assertThat(storage.getFoodList().size(), Is.is(2));
    }

}