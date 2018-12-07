package ru.job4j.exam;


import java.util.*;


public class StringHelper {

    /**
     * Сравинвает два слова по буквам, к примеру маам и мама вернет true, а Маам и мама - false,
     * также мама и мамаа вернет false, если в слова состоят из одинакового набора символо, но количесвтво по
     * каждому символу разное - false, к примеру мама и мааа
     * @param first первое слово
     * @param second второе слово
     * @return
     */
    public boolean checkWordsForSameChars(String first, String second) {
        Map<Character, Integer> firstChars = getCharsWithCount(first);
        Map<Character, Integer> secondChars = getCharsWithCount(second);
        return firstChars.equals(secondChars);
    }

    public Map<Character, Integer> getCharsWithCount(String str) {
        HashMap<Character, Integer> chars = new HashMap<>();
        for (Character ch: str.toCharArray()) {
            chars.computeIfPresent(ch, ((character, integer) -> integer + 1));
            chars.putIfAbsent(ch, 1);
        }
        return chars;
    }
}
