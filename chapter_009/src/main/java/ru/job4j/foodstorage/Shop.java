package ru.job4j.foodstorage;

import java.util.LinkedList;
import java.util.List;

public class Shop implements IStorage {

    private List<IFood> foodList = new LinkedList<>();

    private String code;

    public Shop(String code) {
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
    public List<IFood> getFoodList() {
        return foodList;
    }
}
