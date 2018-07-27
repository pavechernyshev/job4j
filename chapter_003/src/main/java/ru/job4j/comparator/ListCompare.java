package ru.job4j.comparator;

import java.util.Comparator;

public class ListCompare implements Comparator<String> {
    @Override
    public int compare(String left, String right) {
        int res = Integer.compare(left.length(), right.length());
        int strCount = left.length() <= right.length() ? left.length() : right.length();
        for (int i = 0; i < strCount; i++) {
            int charCompare = Character.compare(left.charAt(i), right.charAt(i));
            if (charCompare != 0) {
                res = charCompare;
                break;
            }
        }
        return res;
    }
}