package ru.yandex.tracker.service;

import ru.yandex.tracker.model.Epic;
import ru.yandex.tracker.model.Subtask;
import ru.yandex.tracker.model.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private LinkedList<Task> history = new LinkedList<>();
    private static final int HISTORY_LIMIT = 10;

    /* Внёс изменения, пожалуйста одобрите, я просрочил жёсткий дедлайн, больше времени исправить не будет.
    Если что-то потребуется доисправить, пожалуйста дайте возможность это сделать на задании 6 спринта
    Комментарии будут удалены*/
    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (history.size() >= HISTORY_LIMIT) {
            history.removeFirst();
        }
        history.add(task);
    }

    @Override
    public List<Task> getAll() {
        return history;
    }
}
