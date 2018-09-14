package ru.job4j.exam;

import java.util.*;

import static java.lang.String.format;

public class DepartmentsList {

    class Org implements Comparable<Org> {
        private String parent;
        private String code;

        public String getParent() {
            return parent;
        }

        public String getCode() {
            return code;
        }

        public Org(String code) {
            this.code = code;
            this.parent = this.code.split("\\\\")[0];
        }

        @Override
        public int compareTo(Org o) {
            return this.code.compareTo(o.getCode());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Org org = (Org) o;
            return Objects.equals(code, org.code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code);
        }

        @Override
        public String toString() {
            return this.code;
        }
    }

    public String[] parse(String[] departments, boolean resort) {
        Set<Org> orgs;
        List<String> result = new ArrayList<>();
        if (resort) {
            orgs = new TreeSet<>(new DepartmentsResortComparator());
        } else {
            orgs = new TreeSet<>();
        }
        for (String codes: departments) {
            String[] units = codes.split("\\\\");
            if (units.length > 0) {
                String lvl = units[0];
                orgs.add(new Org(lvl));
                for (int i = 1; i < units.length; i++) {
                    lvl = format("%s\\%s", lvl, units[i]);
                    orgs.add(new Org(lvl));
                }
            }
        }
        for (Org org: orgs) {
            result.add(org.getCode());
        }
        return result.toArray(new String[0]);
    }

    class DepartmentsResortComparator implements Comparator<Org> {
        @Override
        public int compare(Org o1, Org o2) {
            int result = o2.getParent().compareTo(o1.getParent());
            if (result == 0) {
                result = Integer.compare(o1.getCode().length(), o2.getCode().length());
            }
            if (result == 0) {
                result = o2.getCode().compareTo(o1.getCode());
            }

            return result;
        }
    }
}
