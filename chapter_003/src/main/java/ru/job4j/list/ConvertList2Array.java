package ru.job4j.list;

import java.util.ArrayList;
import java.util.List;


public class ConvertList2Array {
    public int[][] toArray(List<Integer> list, int rows) {
        int cells = new Double(Math.ceil(Double.valueOf(list.size()) / Double.valueOf(rows))).intValue();
        int[][] array = new int[rows][cells];
        int position = 0;
        for (int row = 0; row < rows; row++) {
            for (int cell = 0; cell < cells; cell++) {
                if (position < list.size()) {
                    array[row][cell] = list.get(position);
                    position++;
                }
            }
        }
        return array;
    }

    public List<Integer> convert(List<int[]> list) {
        final List<Integer> res = new ArrayList<>();
        list.forEach(i -> {
            for (int num: i) {
                res.add(num);
            }
        });
        return res;
    }
}