package ru.job4j.xmlxsltjdbc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class StoreXML {
    private File target;

    public StoreXML(File target) {
        this.target = target;
    }

    public void save(List<StoreSQL.Entry> list) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Entries.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Entries entries = new Entries(transform(list));
        jaxbMarshaller.marshal(entries, target);
    }

    private List<Entry> transform(List<StoreSQL.Entry> list) {
        List<Entry> entries = new LinkedList<>();
        for (StoreSQL.Entry entry: list) {
            Entry xmlEntry = new Entry(entry.getField());
            entries.add(xmlEntry);
        }
        return entries;
    }

    @XmlRootElement
    public static class Entries {
        private List<Entry> entries;

        public Entries(List<Entry> entries) {
            this.entries = entries;
        }

        public Entries() {
        }
        @XmlElement(name = "entry")
        public List<Entry> getEntries() {
            return entries;
        }

        public void setEntries(List<Entry> entries) {
            this.entries = entries;
        }
    }

    @XmlRootElement
    public static class Entry {
        private int field;

        public Entry(int field) {
            this.field = field;
        }

        public Entry() {

        }

        public int getField() {
            return field;
        }

        public void setField(int field) {
            this.field = field;
        }
    }

}
