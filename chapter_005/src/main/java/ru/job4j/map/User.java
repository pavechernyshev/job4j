package ru.job4j.map;

import java.util.Calendar;
import java.util.Objects;

public class User {
    private String name;
    private Calendar birthday;
    private int children;

    public User(String name, Calendar birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public int getChildren() {
        return children;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return children == user.children
                && Objects.equals(name, user.name)
                && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, children);
    }*/

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\''
                + ", birthday=" + birthday.getTime()
                + ", children=" + children + '}';
    }
}
