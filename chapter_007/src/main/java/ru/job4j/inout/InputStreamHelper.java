package ru.job4j.inout;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamHelper {
    boolean isHasEvenNumber(InputStream in) throws IOException {
        boolean res = false;
        try (BufferedInputStream bis = new BufferedInputStream(in))
        {
            Character enterChar = (char) bis.read();
            int intValue = Integer.parseInt(enterChar.toString());
            if (intValue % 2 == 0) {
                res = true;
            }
        } catch (NumberFormatException nuf) {
            //res = false;
        }
        return res;
    }
}
