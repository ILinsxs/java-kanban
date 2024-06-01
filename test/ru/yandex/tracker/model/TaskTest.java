package ru.yandex.tracker.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    // Проверяет, что два объекта класса Task равны друг другу, если у них одинаковый id
    @Test
    void tasksShouldBeEqualIfIdsAreEqual() {
        Task task1 = new Task(1, "Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(1, "Task 1", "Description 1", Status.NEW);

        assertEquals(task1, task2, "Задачи с одинаковыми id должны быть равны");
    }

    // Проверяет, что два объекта класса Task не равны друг другу, если у них разные id
    @Test
    void tasksShouldNotBeEqualIfIdsAreDifferent() {
        Task task1 = new Task(1, "Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(2, "Task 2", "Description 2", Status.NEW);

        assertNotEquals(task1, task2, "Задачи с разными id не должны быть равны");
    }
}