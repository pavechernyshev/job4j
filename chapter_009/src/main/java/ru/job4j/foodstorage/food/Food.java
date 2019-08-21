package ru.job4j.foodstorage.food;

import java.util.Date;

public abstract class Food implements IFood {
    protected String name;
    protected final Date createdDate;
    protected final Date expireDate;
    protected double price;
    protected double discount;
    protected boolean canReproduct = false;
    protected boolean isVegetable;

    Food(String name, Date createDate, Date expireDate, double price, double discount) {
        this.name = name;
        this.createdDate = createDate;
        this.expireDate = expireDate;
        this.price = price;
        this.discount = discount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String getName() {
        return name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public Date getExpireDate() {
        return expireDate;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public boolean isVegetable() {
        return isVegetable;
    }

    @Override
    public boolean canReproduct() {
        return canReproduct;
    }

    public void setCanReproduct(boolean canReproduct) {
        this.canReproduct = canReproduct;
    }

    public void setIsVegetable(boolean isVegetable) {
        this.isVegetable = isVegetable;
    }
}
