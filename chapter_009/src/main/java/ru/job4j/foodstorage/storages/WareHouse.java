package ru.job4j.foodstorage.storages;

import ru.job4j.foodstorage.food.FoodInspector;
import ru.job4j.foodstorage.food.IFood;

import java.util.LinkedList;
import java.util.List;

public class WareHouse implements IStorage {

    private List<IFood> foodList = new LinkedList<>();

    private String code;

    public WareHouse(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void put(IFood food) {
        foodList.add(food);
    }

    @Override
    public boolean accept(IFood food) {
        int expiredPercent = new FoodInspector().getExpirePercent(food);
        return expiredPercent < 25;
    }

    @Override
    public List<IFood> getFoodList() {
        return foodList;
    }
}
