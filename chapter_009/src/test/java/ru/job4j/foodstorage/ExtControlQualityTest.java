package ru.job4j.foodstorage;

import org.hamcrest.core.Is;
import org.junit.Test;
import ru.job4j.foodstorage.control.ExtControlQuality;
import ru.job4j.foodstorage.food.IFood;
import ru.job4j.foodstorage.food.Milk;
import ru.job4j.foodstorage.food.Potato;
import ru.job4j.foodstorage.storages.ExtStorageFactory;
import ru.job4j.foodstorage.storages.IStorage;
import ru.job4j.foodstorage.storages.IStorageFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ExtControlQualityTest {

    @Test
    public void whenAddTwoProductIntoRecycleStorageAndSecondIsVegetableThenSecondFirstInTheList() {

        IStorageFactory extStorageFactory = new ExtStorageFactory();
        ExtControlQuality controlQuality = new ExtControlQuality(extStorageFactory);
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
                new Date(System.currentTimeMillis() - 86400 * 10),
                new Date(System.currentTimeMillis() - 86400 * 6),
                200,
                0d
        );
        potatoFree.setCanReproduct(true);
        potatoFree.setIsVegetable(true);
        controlQuality.putFood(houseIsTheVillage);
        controlQuality.putFood(potatoFree);
        HashMap<String, IStorage> hashMap = controlQuality.getStorages();
        assertEquals(1, hashMap.size());
        for (IStorage storage: hashMap.values()) {
            List<IFood> foodList = storage.getFoodList();
            assertEquals(2, foodList.size());
            assertThat("Картофель Фрии", Is.is(foodList.get(0).getName()));
            assertThat("Домик в деревне", Is.is(foodList.get(1).getName()));
        }
    }
}