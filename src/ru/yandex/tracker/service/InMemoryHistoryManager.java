package ru.yandex.tracker.service;

import ru.yandex.tracker.model.Epic;
import ru.yandex.tracker.model.Subtask;
import ru.yandex.tracker.model.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    LinkedList<Task> history = new LinkedList<>();
    private static final int HISTORY_LIMIT = 10;

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (history.size() >= HISTORY_LIMIT) {
            history.removeFirst();
        }
        Task taskCopy;
        if (task instanceof Epic) {
            taskCopy = new Epic((Epic) task);
        } else if (task instanceof Subtask) {
            taskCopy = new Subtask((Subtask) task);
        } else {
            taskCopy = new Task(task);
        }
        history.add(taskCopy);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public int getHistorySize() {
        return history.size();
    }

    @Override
    public List<Task> getAll() {
        return new ArrayList<>(history);
    }
}
