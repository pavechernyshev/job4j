package ru.job4j.statistic;

import java.util.*;

public class Store {

    Info diff(List<User> previous, List<User> current) {
        Set<User> pr = new HashSet<>(previous);
        Set<User> cr = new HashSet<>(current);
        int countAdded = getCountAddedUsers(pr, cr);
        int countChanged = getCountChangedUsers(pr, userToHashMap(cr));
        int countDeleted = getCountDeletedUsers(pr, cr);
        return new Info(countAdded, countChanged, countDeleted);
    }

    private Map<User, String> userToHashMap(Set<User> users) {
        Map<User, String> userStringMap = new HashMap<>();
        for (User user: users) {
            userStringMap.put(user, user.name);
        }
        return userStringMap;
    }

    private int getCountAddedUsers(Set<User> previous, Set<User> current) {
        int res = 0;
        for (User user: current) {
            if (!previous.contains(user)) {
                res++;
            }
        }
        return res;
    }

    private int getCountChangedUsers(Set<User> previous, Map<User, String> current) {
        int res = 0;
        for (User user: previous) {
            if (current.containsKey(user) && !user.name.equals(current.get(user))) {
                res++;
            }
        }
        return res;
    }

    private int getCountDeletedUsers(Set<User> previous, Set<User> current) {
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
