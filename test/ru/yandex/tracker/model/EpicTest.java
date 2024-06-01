package ru.yandex.tracker.model;

import org.junit.jupiter.api.Test;
import ru.yandex.tracker.service.Managers;
import ru.yandex.tracker.service.TaskManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    TaskManager taskManager = Managers.getDefault();

    // Проверяет, что два объекта класса Epic равны друг другу, если у них одинаковый id
    @Test
    void epicsShouldBeEqualIfIdsAreEqual() {
        Epic epic1 = new Epic(1, "Epic 1", "Description 1");
        Epic epic2 = new Epic(1, "Epic 1", "Description 1");
        assertEquals(epic1, epic2, "Эпики с одинаковыми id должны быть равны");
    }

    // Проверяет, что два объекта класса Epic не равны друг другу, если у них разные id
    @Test
    void epicsShouldNotBeEqualIfIdsAreDifferent() {
        Epic epic1 = new Epic(1, "Epic 1", "Description 1");
        Epic epic2 = new Epic(2, "Epic 2", "Description 2");
        assertNotEquals(epic1, epic2, "Эпики с разными id не должны быть равны");
    }

    // Проверяет, что объект класса Epic не может быть добавлен в самого себя в виде подзадачи
    @Test
    void epicCannotAddItselfAsSubtask() {
        Epic epic = new Epic(1, "Epic Task", "Description");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask(1, "Subtask", "Description", Status.NEW, 1);
        taskManager.addSubtask(subtask);
        List<Subtask> subtasks = taskManager.getSubtasksByEpicId(1);
        assertFalse(subtasks.contains(subtask), "Эпик не должен добавлять себя в виде подзадачи");
    }
}