package ru.job4j.inout;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamHelper {
    boolean isNumber(InputStream in) {
        boolean res = false;
        try {
            Character enterChar = (char) in.read();
            Integer.parseInt(enterChar.toString());
            res = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException nuf) {
            res = false;
        }
        return res;
    }
}
