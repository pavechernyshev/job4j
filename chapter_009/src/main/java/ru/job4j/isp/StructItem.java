package ru.job4j.isp;

import java.util.LinkedList;
import java.util.List;

public class StructItem implements IStructItem {

    private String name;
    private List<IStructItem> subItems = new LinkedList<>();

    public StructItem(String name) {
        this.name = name;
    }

    @Override
    public void addSubItem(IStructItem menuItem) {
        subItems.add(menuItem);
    }

    @Override
    public List<IStructItem> getSubItems() {
        return subItems;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void execute() {
        // todo: something
    }
}
