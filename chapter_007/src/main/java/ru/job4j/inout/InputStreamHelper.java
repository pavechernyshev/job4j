package ru.job4j.inout;

import java.io.*;


public class InputStreamHelper {
    boolean isHasEvenNumber(InputStream in) throws IOException {
        boolean res = false;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in)))
        {
            // чтение построчно
            while(bufferedReader.ready()) {
                int intValue = Integer.parseInt(bufferedReader.readLine());
                if (intValue % 2 == 0) {
                    res = true;
                }
            }
        }
        return res;
    }
}
