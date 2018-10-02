package ru.job4j.generics;

/***
 * @author Павел Чернышев (pavel.chernyshev.work@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class RoleStore extends AbstractStore<Role> implements Store<Role> {
    public RoleStore(int size) {
        super(new SimpleArray<>(size));
    }
}
