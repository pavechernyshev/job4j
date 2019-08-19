package ru.job4j.isp;

import java.util.List;

public interface IStructItem extends IMenuItem {
    void addSubItem(IStructItem menuItem);
    List<IStructItem> getSubItems();
}
