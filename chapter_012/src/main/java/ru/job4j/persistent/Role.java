package ru.job4j.persistent;

import ru.job4j.models.User;

import java.util.List;

public interface Role {
    ru.job4j.models.Role getUserRole(User user);
    List<ru.job4j.models.Role> getRoles();
}
