package ru.job4j.logic;

import ru.job4j.models.User;
import ru.job4j.persistent.DbStore;
import ru.job4j.persistent.Role;

import java.util.List;

public class RoleService implements Role {

    public static final int ADMIN_ROLE_ID = 1;
    public static final int USER_ROLE_ID = 2;

    private final static RoleService INSTANCE = new RoleService();

    private RoleService() {

    }

    public static RoleService getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public ru.job4j.models.Role getUserRole(User user) {
        return DbStore.getINSTANCE().getUserRole(user);
    }

    @Override
    public List<ru.job4j.models.Role> getRoles() {
        return DbStore.getINSTANCE().getRoles();
    }
}
