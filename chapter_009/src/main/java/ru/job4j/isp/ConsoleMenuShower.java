package ru.job4j.isp;

import java.util.LinkedList;
import java.util.List;

public class ConsoleMenuShower implements  IStructMenuShower {

    List<IStructItem> menu = new LinkedList<>();
    private String lvlSeparator = "-";

    public void setLvlSeparator(String lvlSeparator) {
        this.lvlSeparator = lvlSeparator;
    }

    @Override
    public void addRootItem(IStructItem structItem) {
        menu.add(structItem);
    }

    @Override
    public void show() {
        for (IStructItem item: menu) {
            System.out.println(item.getName());
            printSubItems(item, lvlSeparator);
        }
    }

    private void printSubItems(IStructItem item, String lvlSeparator) {
        if (hasSubItems(item)) {
            for (IStructItem subItem: item.getSubItems()) {
                System.out.println(lvlSeparator + subItem.getName());
                printSubItems(subItem, lvlSeparator + this.lvlSeparator);
            }
        }
    }

    private boolean hasSubItems(IStructItem item) {
        return item.getSubItems().size() > 0;
    }
}
