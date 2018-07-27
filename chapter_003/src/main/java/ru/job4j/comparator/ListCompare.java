package ru.job4j.comparator;

import java.util.Comparator;

public class ListCompare implements Comparator<String> {
    @Override
    public int compare(String left, String right) {
        int res = left.equals(right) ? 0 : -1;
        int strCount = Integer.compare(left.length(), right.length()) <= 0 ? left.length() : right.length();
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