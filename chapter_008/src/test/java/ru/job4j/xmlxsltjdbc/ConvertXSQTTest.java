package ru.job4j.xmlxsltjdbc;

import org.junit.Test;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ConvertXSQTTest {

    @Test
    public void convert() throws FileNotFoundException, TransformerException {
        File source = new File("src/test/java/ru/job4j/xmlxsltjdbc/file.xml");
        File shame = new File("src/test/java/ru/job4j/xmlxsltjdbc/shame.xsl");
        File dist = new File("dist.xml");
        ConvertXSQT convertXSQT = new ConvertXSQT();
        convertXSQT.convert(source, dist, shame);
    }
}