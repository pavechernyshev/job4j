package ru.job4j.foodstorage;

public class FoodInspector {

    public int getExpirePercent(IFood food) {
        int res = 100;
        if (!isProductDefective(food)) {
            long createProduct = food.getCreatedDate().getTime();
            long expireProduct = food.getExpireDate().getTime();
            long cutTime = System.currentTimeMillis();
            long percentTime = (expireProduct - createProduct) / 100;
            res = 100 - (int)((expireProduct - cutTime) / percentTime);
        }
        return res;
    }

    /**
     *
     * @param food IFood
     * @return int days count
     * @throws ArithmeticException if dates are not correctly
     */
    public int getStorageLifeDays(IFood food) throws ArithmeticException{
        long createProduct = food.getCreatedDate().getTime();
        long expireProduct = food.getExpireDate().getTime();
        if (expireProduct < createProduct) {
            throw new ArithmeticException("Created date and expire date ara not correct");
        }
        return (int)(expireProduct - createProduct) / 86400;
    }

    public boolean isProductDefective(IFood food) {
        return food.getExpireDate().getTime() < System.currentTimeMillis();
    }
}
