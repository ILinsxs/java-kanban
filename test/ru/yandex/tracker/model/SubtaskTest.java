package ru.yandex.tracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.tracker.service.Managers;
import ru.yandex.tracker.service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    private TaskManager taskManager = Managers.getDefault();

    // Проверяет, что два объекта класса Subtask равны друг другу, если у них одинаковый id
    @Test
    void subtasksShouldBeEqualIfIdsAreEqual() {
        Subtask subtask1 = new Subtask(1, "Subtask 1", "Description 1", Status.NEW, 1);
        Subtask subtask2 = new Subtask(1, "Subtask 1", "Description 1", Status.NEW, 1);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковыми id должны быть равны");
    }

    // Проверяет, что два объекта класса Subtask не равны друг другу, если у них разные id
    @Test
    void subtasksShouldNotBeEqualIfIdsAreDifferent() {
        Subtask subtask1 = new Subtask(1, "Subtask 1", "Description 1", Status.NEW, 1);
        Subtask subtask2 = new Subtask(2, "Subtask 2", "Description 2", Status.NEW, 1);

        assertNotEquals(subtask1, subtask2, "Подзадачи с разными id не должны быть равны");
    }

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
    }

    // Проверяет, что подзадача не может ссылаться на свой же эпик
    @Test
    void subtaskCannotBeItsOwnEpic() {
        Epic epic = new Epic(0, "Test Epic", "Test Epic description");
        int epicId = taskManager.addEpic(epic);

        Subtask subtask = new Subtask(0, "Test Subtask", "Test Subtask description", Status.NEW, epicId);
        subtask.setId(epicId);

        Subtask addedSubtask = taskManager.addSubtask(subtask);
        assertNull(addedSubtask, "Подзадача не может быть своим же эпиком.");
    }
}