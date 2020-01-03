package ru.job4j.xmlxsltjdbc;
import javax.xml.parsers.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import ru.job4j.log.UsageLog4j2;

import java.io.*;

public class SAXParser {
    private long sum = 0;
    private static final Logger LOG = LogManager.getLogger(UsageLog4j2.class.getName());

    public void showSumFieldAttr(File file) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        javax.xml.parsers.SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        XMLHandler handler = new XMLHandler();
        xmlReader.setContentHandler(handler);
        xmlReader.parse(file.getAbsolutePath());
        LOG.info(sum);
    }

    public long getSum() {
        return sum;
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
