package ru.job4j.sort;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SortUser {
    public Set<User> sort(List<User> users) {
        return new TreeSet<>(users);
    }

    public List<User> sortNameLength(List<User> users) {
        users.sort(new CompareNameLength());
        return users;
    }

    public List<User> sortByAllFields(List<User> users) {
        users.sort(new CompareNameLength().thenComparing(new CompareAge()));
        return users;
    }

    class CompareNameLength implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            return Integer.compare(o1.getName().length(), o2.getName().length());
        }
    }

    class CompareAge implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            return Integer.compare(o1.getAge(), o2.getAge());
        }
    }
}
