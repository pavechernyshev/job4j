package ru.job4j.xmlxsltjdbc;

import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class StoreXMLTest {

    @Test
    public void save() throws IOException, JAXBException, SQLException {
        File file = new File("src/test/java/ru/job4j/xmlxsltjdbc/file.xml");
        if (!file.exists()) {
            file.createNewFile();
        }
        Config config = new Config();
        config.init();
        StoreSQL storeSQL = new StoreSQL(config);
        storeSQL.init();
        storeSQL.generate(5);
        List<StoreSQL.Entry> list = storeSQL.load();
        storeSQL.clear();
        StoreXML storeXML = new StoreXML(file);
        storeXML.save(list);
    }
}