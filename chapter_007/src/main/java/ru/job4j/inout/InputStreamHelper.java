package ru.job4j.inout;

import java.io.*;


public class InputStreamHelper {
    boolean isHasEvenNumber(InputStream in) throws IOException {
        boolean res = false;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in)))
        {
            // чтение посимвольно
            while(bufferedReader.ready()) {
                Character currentProcessChar = (char) bufferedReader.read();
                int intValue = Integer.parseInt(currentProcessChar.toString());
                if (intValue % 2 == 0) {
                    res = true;
                }
            }
        }
        return res;
    }
}
