package ru.job4j.foodstorage;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class FoodInspectorTest {

    FoodInspector foodInspector = new FoodInspector();


    @Test
    public void getStorageLifeDays() {
        Milk funMilker = new Milk(
                "Веселый молочник",
                (new Date(System.currentTimeMillis())),
                new Date(System.currentTimeMillis() + (86400) * 10),
                100d,
                0d
        );
        Milk houseIsTheVillage = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - (86400) * 2),
                new Date(System.currentTimeMillis() + (86400) * 10),
                100d,
                0d
        );
        Assert.assertThat(foodInspector.getStorageLifeDays(funMilker), Is.is(10));
        Assert.assertThat(foodInspector.getStorageLifeDays(houseIsTheVillage), Is.is(12));
    }

    @Test
    public void whenProductDefective() {
        Milk houseIsTheVillage = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - (86400) * 10),
                new Date(System.currentTimeMillis() - (86400) * 2),
                100d,
                0d
        );
        Assert.assertTrue(foodInspector.isProductDefective(houseIsTheVillage));
        houseIsTheVillage.getExpireDate().setTime(System.currentTimeMillis() + 86400);
        Assert.assertFalse(foodInspector.isProductDefective(houseIsTheVillage));
    }

    @Test
    public void whenTenPercentExpired() {
        Milk houseIsTheVillage = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - 86400),
                new Date(System.currentTimeMillis() + (86400) * 10),
                100d,
                0d
        );
        Assert.assertThat(foodInspector.getExpirePercent(houseIsTheVillage), Is.is(10));
    }

    @Test
    public void whenSeventyFivePercentExpired() {
        Milk houseIsTheVillage = new Milk(
                "Домик в деревне",
                new Date(System.currentTimeMillis() - 86400 * 75),
                new Date(System.currentTimeMillis() + (86400) * 25),
                100d,
                0d
        );
        Assert.assertThat(foodInspector.getExpirePercent(houseIsTheVillage), Is.is(75));
    }
}