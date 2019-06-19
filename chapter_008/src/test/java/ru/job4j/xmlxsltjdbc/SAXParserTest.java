package ru.job4j.xmlxsltjdbc;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import static org.junit.Assert.*;

public class SAXParserTest {

    @Test
    public void start() throws IOException, SAXException, ParserConfigurationException {
        File file = new File("src/test/java/ru/job4j/xmlxsltjdbc/dist.xml");
        SAXParser saxParser = new SAXParser();
        StringBuilder sb = new StringBuilder();
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                sb.append((char) b);
            }
        });
        System.setOut(printStream);
        saxParser.showSumFieldAttr(file);
        assertThat(Integer.parseInt(sb.toString()), Is.is(15));
    }
}