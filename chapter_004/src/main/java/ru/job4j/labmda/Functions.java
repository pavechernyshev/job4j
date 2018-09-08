package ru.job4j.labmda;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Functions {

    public List<Double> diapason(int start, int end, Function<Double, Double> func) {
        List<Double> res = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            res.add(func.apply(Double.valueOf(i)));
        }
        return res;
    }
}
