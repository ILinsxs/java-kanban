package ru.yandex.tracker.service;

import ru.yandex.tracker.model.Epic;
import ru.yandex.tracker.model.Subtask;
import ru.yandex.tracker.model.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private LinkedList<Task> history = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (history.size() >= 10) {
            history.removeFirst();
        }
        Task taskCopy = task.copy();
        history.add(taskCopy);
    }

//    @Override
//    public List<Task> getHistory() {
//        return history;
//    }
//
//    @Override
//    public int getHistorySize() {
//        return history.size();
//    }

    @Override
    public List<Task> getAll() {
        return history;
    }
}
