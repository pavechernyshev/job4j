package ru.job4j.inout;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;


public class InputStreamHelper {
    public boolean isHasEvenNumber(InputStream in) throws IOException {
        boolean res = false;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in))) {
            // чтение построчно
            while (bufferedReader.ready()) {
                int intValue = Integer.parseInt(bufferedReader.readLine());
                if (intValue % 2 == 0) {
                    res = true;
                }
            }
        }
        return res;
    }

    public void dropAbuses(InputStream in, OutputStream out, String[] abuses) {
        String abuseRegex =  String.join("|", abuses);
        try (Scanner scanner = new Scanner(in)) {
            while (scanner.hasNextLine()) {
                String lineWithoutAbuses = scanner.nextLine().replaceAll(abuseRegex, "");
                out.write(lineWithoutAbuses.getBytes(Charset.forName("UTF-8")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
