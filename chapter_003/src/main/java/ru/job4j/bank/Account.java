package ru.job4j.bank;

import java.util.Objects;

public class Account {
    private String requisites;
    private int value;

    Account(String requisites, int value) {
        this.requisites = requisites;
        this.value = value;
    }

    public String getRequisites() {
        return requisites;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return requisites.equals(account.requisites)
                && value == account.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requisites, value);
    }
}
