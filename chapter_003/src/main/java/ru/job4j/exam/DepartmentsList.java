package ru.job4j.exam;

import java.util.*;

import static java.lang.String.format;

public class DepartmentsList {
    private Set<String> result = new TreeSet<>();

    public String[] parse(String[] departments) {
        for (String codes: departments) {
            String[] units = codes.split("\\\\");
            if (units.length > 0) {
                String lvl = units[0];
                result.add(lvl);
                for (int i = 1; i < units.length; i++) {
                    lvl = format("%s\\%s", lvl, units[i]);
                    result.add(lvl);
                }
            }
        }
        return result.toArray(new String[0]);
    }

    public String[] resort() {
        Set<String> resort =  new TreeSet<>(new DepartmentsResortComparator());
        resort.addAll(result);
        return resort.toArray(new String[0]);
    }

    class DepartmentsResortComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            String unit1 = o1.split("\\\\")[0];
            String unit2 = o2.split("\\\\")[0];
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

}
