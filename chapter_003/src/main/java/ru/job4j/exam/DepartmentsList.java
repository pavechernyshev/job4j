package ru.job4j.exam;

import java.util.*;

import static java.lang.String.format;

public class DepartmentsList {

    class DepartmentsComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    class DepartmentsResortComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            String unit1 = o1.split(format("\\%s", separator))[0];
            String unit2 = o2.split(format("\\%s", separator))[0];
            return unit2.compareTo(unit1);
        }
    }

    class LengthCompare implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return Integer.compare(o1.length(), o2.length());
        }
    }

    private String separator = "\\";
    private List<String> departments = new LinkedList<>();

    public void add(String codes) {
        this.departments.add(codes);

        String[] units = codes.split(format("\\%s", separator));
        if (units.length > 1) {
            String check = units[0];
            for (int i = 0; i < units.length - 1; i++) {
                if (!this.departments.contains(check)) {
                    this.departments.add(check);
                }
                check = format("%s%s%s", check, separator, units[i + 1]);
            }
        }
    }

    public List<String> sort() {
        departments.sort(new DepartmentsComparator());
        return this.departments;
    }

    public List<String> resort() {
        departments.sort(new DepartmentsResortComparator().thenComparing(new LengthCompare())
                .thenComparing(new DepartmentsComparator().reversed()));
        return this.departments;
    }

}
