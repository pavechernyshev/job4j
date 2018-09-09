package ru.job4j.exam;

import java.util.*;

import static java.lang.String.format;

public class DepartmentsList {

    class DepartmentsResortComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            String unit1 = split(o1)[0];
            String unit2 = split(o2)[0];
            int res = unit2.compareTo(unit1);
            if (res == 0) {
                res = Integer.compare(o1.length(), o2.length());
            }
            if (res == 0) {
                res = o2.compareTo(o1);
            }
            return res;
        }
    }

    private String separator = "\\";
    private Set<String> departments = new TreeSet<>();

    public void add(String codes) {
        this.departments.add(codes);
        String[] units = split(codes);
        if (units.length > 1) {
            String check = units[0];
            for (int i = 0; i < units.length - 1; i++) {
                if (!departments.contains(check)) {
                    this.departments.add(check);
                }
                check = format("%s%s%s", check, separator, units[i + 1]);
            }
        }
    }

    public Set<String> sort() {
        return this.departments;
    }

    public Set<String> resort() {
        Set<String> resort =  new TreeSet<>(new DepartmentsResortComparator());
        resort.addAll(this.departments);
        return resort;
    }

    private String[] split(String str) {
        return str.split(format("\\%s", this.separator));
    }

}
