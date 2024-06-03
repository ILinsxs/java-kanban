package ru.yandex.tracker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.tracker.model.Status;
import ru.yandex.tracker.model.Task;

import static org.junit.jupiter.api.Assertions.*;

class inMemoryHistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void addToHistory() {
        Task task = new Task(0, "Test addToHistory", "Test addToHistory description", Status.NEW);
        historyManager.add(task);
        var history = historyManager.getAll();
        assertNotNull(history, "История не пуста.");
        assertEquals(1, history.size(), "История не пуста.");
        assertEquals(task, history.get(0), "Задачи не совпадают.");
    }

    @Test
    void shouldKeepPreviousVersionOfTask() {
        Task task = new Task(1, "Task 1", "Description 1", Status.NEW);
        historyManager.add(task);

        Task updatedTask = new Task(task);
        updatedTask.setStatus(Status.IN_PROGRESS);
        historyManager.add(updatedTask);

        var history = historyManager.getAll();
        assertEquals(2, history.size(), "История должна содержать две версии задачи");
        assertEquals(Status.NEW, history.get(0).getStatus(), "Первая версия задачи должна быть со статусом NEW");
        assertEquals(Status.IN_PROGRESS, history.get(1).getStatus(), "Вторая версия задачи должна быть со статусом IN_PROGRESS");
    }
}