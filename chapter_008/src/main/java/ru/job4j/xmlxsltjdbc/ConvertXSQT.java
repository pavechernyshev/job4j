package ru.job4j.xmlxsltjdbc;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConvertXSQT {
    public void convert(File source, File dist, File scheme) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        byte[] shameBytes = getFileBytes(scheme);
        Transformer transformer = factory.newTransformer(
                new StreamSource(
                        new ByteArrayInputStream(shameBytes))
        );
        transformer.transform(new StreamSource(
                        new ByteArrayInputStream(getFileBytes(source))),
                new StreamResult(dist)
        );
    }

    private byte[] getFileBytes(File file) {
        StringBuilder fileSB = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                fileSB.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileSB.toString().getBytes();
    }
}
