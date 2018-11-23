package ru.job4j.statistic;

import java.util.Objects;

public class Info {
    private int countAddedUsers;
    private int countChangedUsers;
    private int countDeletedUsers;

    Info(int countAddedUsers, int countChangedUsers, int countDeletedUsers) {

        this.countAddedUsers = countAddedUsers;
        this.countChangedUsers = countChangedUsers;
        this.countDeletedUsers = countDeletedUsers;
    }

    public int getCountAddedUsers() {
        return countAddedUsers;
    }

    public int getCountChangedUsers() {
        return countChangedUsers;
    }

    public int getCountDeletedUsers() {
        return countDeletedUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass())  {
            return false;
        }
        Info info = (Info) o;
        return countAddedUsers == info.countAddedUsers
                && countChangedUsers == info.countChangedUsers
                && countDeletedUsers == info.countDeletedUsers;
    }

    @Override
    public int hashCode() {
        return Objects.hash(countAddedUsers, countChangedUsers, countDeletedUsers);
    }

    @Override
    public String toString() {
        return "Info{" + "countAddedUsers=" + countAddedUsers
                + ", countChangedUsers=" + countChangedUsers
                + ", countDeletedUsers=" + countDeletedUsers
                + '}';
    }
}
