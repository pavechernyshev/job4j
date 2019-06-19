package ru.job4j.xmlxsltjdbc;
import javax.xml.parsers.*;

import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2DTM;
import org.xml.sax.*;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

public class SAXParser {
    private long sum = 0;

    public void showSumFieldAttr(File file) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        javax.xml.parsers.SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        XMLHandler handler = new XMLHandler();
        xmlReader.setContentHandler(handler);
        xmlReader.parse(file.getAbsolutePath());
        System.out.print(sum);
    }

    private class XMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("entry")) {
                int field = Integer.parseInt(attributes.getValue("field"));
                sum += field;
            }
        }
    }
}
