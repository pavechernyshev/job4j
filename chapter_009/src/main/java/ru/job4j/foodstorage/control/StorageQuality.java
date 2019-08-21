package ru.job4j.foodstorage.control;

import ru.job4j.foodstorage.food.IFood;
import ru.job4j.foodstorage.storages.*;
import java.util.Comparator;
import java.util.LinkedList;

public class StorageQuality {

    protected LinkedList<StorageNode> storageNodes = new LinkedList<>();

    public void init() {
    }

    public IStorage accept(IFood food) {
        IStorage storage = getStorage(food);
        storage.put(food);
        return storage;
    }

    public void addStorage(IStorage storage, int priority) {
        StorageNode storageNode = new StorageNode(storage, priority);
        storageNodes.add(storageNode);
        storageNodes.sort(Comparator.comparingInt(StorageNode::getPriority));
    }

    protected class StorageNode {
        private IStorage storage;
        private int priority;

        public StorageNode(IStorage storage, int priority) {
            this.priority = priority;
            this.storage = storage;
        }

        public int getPriority() {
            return priority;
        }

        public IStorage getStorage() {
            return storage;
        }
    }

    protected IStorage getStorage(IFood food) {
        IStorage res = null;
        for (StorageNode storageNode: storageNodes) {
            if (storageNode.getStorage().accept(food)) {
                res = storageNode.getStorage();
                break;
            }
        }
        return res;
    }
}
