package ru.job4j.generics;

/***
 * @author Павел Чернышев (pavel.chernyshev.work@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {
    protected SimpleArray<T> simpleArray;
    public AbstractStore(SimpleArray<T> simpleArray) {
        this.simpleArray = simpleArray;
    }

    protected int findPosById(String id) {
        int result = -1;
        for (int index = 0; index < simpleArray.getSize(); index++) {
            T u = simpleArray.get(index);
            if (u != null && u.getId().equals(id)) {
                result = index;
                break;
            }
        }
        return result;
    }

    @Override
    public void add(T model) {
        this.simpleArray.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
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
    public T findById(String id) {
        T result = null;
        for (T u : simpleArray) {
            if (u != null && u.getId().equals(id)) {
                result = u;
                break;
            }
        }
        return result;
    }

}
