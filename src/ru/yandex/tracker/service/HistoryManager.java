package ru.yandex.tracker.service;

import ru.yandex.tracker.model.Task;
import java.util.List;

public interface HistoryManager {
    void add(Task task);
    List<Task> getHistory();
    List<Task> getAll();
    int getHistorySize();
}
