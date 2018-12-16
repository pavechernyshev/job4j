package ru.job4j.nonblocking;

import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    ConcurrentHashMap<Integer, Base> concurrentHashMap = new ConcurrentHashMap<>();

    public void add(Base model) {
        concurrentHashMap.put(model.getId(), model);
    }

    public void update(Base model) {
        concurrentHashMap.computeIfPresent(model.getId(),
            (integer, base) -> {
                if (model.getVersion() != base.getVersion()) {
                    throw new OptimisticException();
                }
                base.upVersion();
                return base;
            }
        );
    }

    public boolean delete(Base model) {
        return concurrentHashMap.remove(model.getId(), model);
    }
}
