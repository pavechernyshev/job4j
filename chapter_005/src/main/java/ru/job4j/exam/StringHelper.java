package ru.job4j.exam;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class StringHelper {

    /**
     * Сравинвает два слова по буквам, к примеру маам и мама вернет true, а Маам и мама - false,
     * также мама и мамаа вернет false
     * @param w1
     * @param w2
     * @return
     */
    public boolean equalOnChars(String w1, String w2) {
        boolean res = false;
        if (w1.length() == w2.length()) {
            Set<Character> w1Set = new HashSet<>();
            Set<Character> w2Set = new HashSet<>();
            Collections.addAll(w1Set, convertCharToCharacter(w1.toCharArray()));
            Collections.addAll(w2Set, convertCharToCharacter(w2.toCharArray()));
            res = w1Set.equals(w2Set);
        }
        return res;
    }

    private Character[] convertCharToCharacter(char[] chars) {
        Character[] res = new Character[chars.length];
        for (int i = 0; i < chars.length; i++) {
            res[i] = chars[i];
        }
        return res;
    }
}
