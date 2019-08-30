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
        LinkedList<SMLvlItem> itemsForShow = new LinkedList<>(convert(menu, 0));
        while (itemsForShow.size() > 0) {
            SMLvlItem parent = itemsForShow.removeFirst();
            print(parent);
            if (hasSubItems(parent)) {
                LinkedList<SMLvlItem> subItemsStack = new LinkedList<>(convert(parent.getSubItems(), parent.lvl + 1));
                while (subItemsStack.size() > 0) {
                    itemsForShow.addFirst(subItemsStack.removeLast());
                }
            }
        }
    }

    private List<SMLvlItem> convert(List<IStructItem> list, int lvl) {
        List<SMLvlItem> res = new LinkedList<>();
        for (IStructItem item: list) {
            res.add(new SMLvlItem(item, lvl));
        }
        return res;
    }

    private String getLvlSeparator(int subLvl) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < subLvl; i++) {
            sb.append(lvlSeparator);
        }
        return sb.toString();
    }

    private void print(SMLvlItem item) {
        System.out.println(getLvlSeparator(item.lvl) + item.getName());
    }

    private boolean hasSubItems(IStructItem item) {
        return item.getSubItems().size() > 0;
    }

    class SMLvlItem implements IStructItem {
        final IStructItem iStructItem;
        int lvl;

        public SMLvlItem(IStructItem iStructItem, int lvl) {
            this.iStructItem = iStructItem;
            this.lvl = lvl;
        }

        @Override
        public void addSubItem(IStructItem menuItem) {
            iStructItem.addSubItem(menuItem);
        }

        @Override
        public List<IStructItem> getSubItems() {
            return iStructItem.getSubItems();
        }

        @Override
        public String getName() {
            return iStructItem.getName();
        }

        @Override
        public void execute() {
            iStructItem.execute();
        }

        public int getLvl() {
            return lvl;
        }

        public void setLvl(int lvl) {
            this.lvl = lvl;
        }
    }
}
