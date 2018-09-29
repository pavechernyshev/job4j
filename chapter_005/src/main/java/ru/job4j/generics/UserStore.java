package ru.job4j.generics;

import javax.jws.soap.SOAPBinding;

/***
 * @author Павел Чернышев (pavel.chernyshev.work@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserStore implements Store<User> {

    private SimpleArray<User> simpleArray;

    public UserStore(int size) {
        this.simpleArray = new SimpleArray<>(size);
    }

    @Override
    public void add(User model) {
        this.simpleArray.add(model);
    }

    @Override
    public boolean replace(String id, User model) {
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
    public User findById(String id) {
        User result = null;
        for (User u : simpleArray) {
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
            User u = simpleArray.get(index);
            if (u != null && u.getId().equals(id)) {
                result = index;
                break;
            }
        }
        return result;
    }
}
