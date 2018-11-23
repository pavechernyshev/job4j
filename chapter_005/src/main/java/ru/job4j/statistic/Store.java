package ru.job4j.statistic;

import java.util.List;
import java.util.Objects;

public class Store {

    Info diff(List<User> previous, List<User> current) {
        int countAdded = getCountAddedUsers(previous, current);
        int countChanged = getCountChangedUsers(previous, current);
        int countDeleted = getCountDeletedUsers(previous, current);
        return new Info(countAdded, countChanged, countDeleted);
    }

    private int getCountAddedUsers(List<User> previous, List<User> current) {
        int res = 0;
        for (User user: current) {
            if (!previous.contains(user)) {
                res++;
            }
        }
        return res;
    }

    private int getCountChangedUsers(List<User> previous, List<User> current) {
        int res = 0;
        for (User user: previous) {
            int index = current.indexOf(user);
            if (index != -1 && !user.name.equals(current.get(index).name)) {
                res++;
            }
        }
        return res;
    }

    private int getCountDeletedUsers(List<User> previous, List<User> current) {
        int res = 0;
        for (User user: previous) {
            if (!current.contains(user)) {
                res++;
            }
        }
        return res;
    }

    static class User {
        int id;
        String name;

        User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            User user = (User) o;
            return id == user.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "User{" + "id=" + id + ", name='" + name + '\'' + '}';
        }
    }

}
