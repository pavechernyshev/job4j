package ru.job4j.generics;

/***
 * @author Павел Чернышев (pavel.chernyshev.work@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class RoleStore implements Store<Role> {
    private SimpleArray<Role> simpleArray;

    public RoleStore(int size) {
        this.simpleArray = new SimpleArray<>(size);
    }

    @Override
    public void add(Role model) {
        this.simpleArray.add(model);
    }

    @Override
    public boolean replace(String id, Role model) {
        int pos = findPosById(id);
        boolean result = pos >= 0;
        if (result) {
            this.simpleArray.set(pos, model);
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        int pos = findPosById(id);
        boolean result = pos >= 0;
        if (result) {
            simpleArray.delete(pos);
        }
        return result;
    }

    @Override
    public Role findById(String id) {
        Role result = null;
        for (Role u : simpleArray) {
            if (u != null && u.getId().equals(id)) {
                result = u;
                break;
            }
        }
        return result;
    }

    private int findPosById(String id) {
        int result = -1;
        for (int index = 0; index < simpleArray.getSize(); index++) {
            Role u = simpleArray.get(index);
            if (u != null && u.getId().equals(id)) {
                result = index;
                break;
            }
        }
        return result;
    }
}
