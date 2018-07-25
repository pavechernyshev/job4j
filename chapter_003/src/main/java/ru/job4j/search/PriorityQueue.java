package ru.job4j.search;

import java.util.LinkedList;

public class PriorityQueue {
    private LinkedList<Task> tasks = new LinkedList<>();

    /**
     * Метод должен вставлять в нужную позицию элемент.
     * Позиция определять по полю приоритет.
     * Для вставик использовать add(int index, E value)
     * @param task задача
     */
    public void put(Task task) {
        if (tasks.isEmpty()) {
            tasks.add(task);
        } else {
            int position = 0;
            for (Task listTask: tasks) {
                if (task.getPriority() <= listTask.getPriority()) {
                    tasks.add(position, task);
                    break;
                }
                position++;
            }
            if (position == tasks.size()) {
                tasks.add(task);
            }
        }
    }

    public Task take() {
        return this.tasks.poll();
    }
}
