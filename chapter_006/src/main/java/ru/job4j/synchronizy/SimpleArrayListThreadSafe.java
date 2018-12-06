package ru.job4j.synchronizy;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.list.SimpleArrayList;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@ThreadSafe
public class SimpleArrayListThreadSafe<E> implements Iterable<E> {
    @GuardedBy("this")
    SimpleArrayList<E> sal;

    SimpleArrayListThreadSafe(SimpleArrayList<E> sal) {
        this.sal = sal;
    }

    public synchronized void add(E data) {
        sal.add(data);
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return copy(this.sal).iterator();
    }

    private synchronized SimpleArrayList<E> copy(SimpleArrayList<E> sal) {
        SimpleArrayList<E> res = new SimpleArrayList<>();
        for (E item: sal) {
            res.add(item);
        }
        return res;
    }
}
