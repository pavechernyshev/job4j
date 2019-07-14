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
        File file = new File("dist.xml");
        SAXParser saxParser = new SAXParser();
        saxParser.showSumFieldAttr(file);
        assertThat(saxParser.getSum(), Is.is(15L));
    }
}