package headfirst.patterns.decorator.input;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class LowerCaseInputStreamTest {
    @Test
    public void whenLoadFile() throws FileNotFoundException, IOException {
        int c;
        String res = "";
        InputStream in = new LowerCaseInputStream(
            new BufferedInputStream(
                    new FileInputStream("C:\\projects\\job4j\\books\\src\\test\\java\\headfirst\\patterns\\decorator\\input\\text.txt")
            )
        );
        while ((c = in.read()) >= 0) {
            res += (char) c;
        }
        assertThat(res, Is.is("i know the decorator pattern therefore i rule!"));
    }

}