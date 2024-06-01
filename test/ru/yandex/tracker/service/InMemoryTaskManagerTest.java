package ru.yandex.tracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.tracker.model.Epic;
import ru.yandex.tracker.model.Status;
import ru.yandex.tracker.model.Subtask;
import ru.yandex.tracker.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
    }

    // Проверяет, что задача успешно добавляется и может быть найдена по id
    @Test
    void addNewTask() {
        Task task = new Task(0, "Test addNewTask", "Test addNewTask description", Status.NEW);
        int taskId = taskManager.addTask(task);
        Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        var tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    // Проверяет, что эпик успешно добавляется и может быть найден по id
    @Test
    void addNewEpic() {
        Epic epic = new Epic(0, "Test addNewEpic", "Test addNewEpic description");
        int epicId = taskManager.addEpic(epic);
        Epic savedEpic = taskManager.getEpic(epicId);

        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");

        var epics = taskManager.getAllEpics();

        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(epic, epics.get(0), "Эпики не совпадают.");
    }

    // Проверяет, что подзадача успешно добавляется и может быть найдена по id
    @Test
    void addNewSubtask() {
        Epic epic = new Epic(0, "Test Epic", "Test Epic description");
        int epicId = taskManager.addEpic(epic);
        Subtask subtask = new Subtask(0, "Test addNewSubtask", "Test addNewSubtask description", Status.NEW, epicId);
        Subtask addedSubtask = taskManager.addSubtask(subtask);
        Subtask savedSubtask = taskManager.getSubtask(addedSubtask.getId());

        assertNotNull(savedSubtask, "Подзадача не найдена.");
        assertEquals(subtask, savedSubtask, "Подзадачи не совпадают.");

        var subtasks = taskManager.getAllSubtasks();

        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(subtask, subtasks.get(0), "Подзадачи не совпадают.");
    }

    // Проверка уникальности ID
    @Test
    void tasksShouldHaveUniqueIds() {
        Task task1 = new Task(0, "Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(0, "Task 2", "Description 2", Status.NEW);
        int id1 = taskManager.addTask(task1);
        int id2 = taskManager.addTask(task2);
        assertNotEquals(id1, id2, "ID задач должны быть уникальными");
    }

    // Проверка неизменности задачи при добавлении
    @Test
    void taskShouldRemainUnchangedWhenAdded() {
        Task task = new Task(0, "Task 1", "Description 1", Status.NEW);
        Task originalTask = new Task(task);

        int taskId = taskManager.addTask(task);
        Task savedTask = taskManager.getTask(taskId);

        assertNotEquals(originalTask.getId(), savedTask.getId(), "ID задачи должен изменяться при добавлении в менеджер");
        assertEquals(originalTask.getTitle(), savedTask.getTitle(), "Название задачи не должно изменяться при добавлении в менеджер");
        assertEquals(originalTask.getDescription(), savedTask.getDescription(), "Описание задачи не должно изменяться при добавлении в менеджер");
        assertEquals(originalTask.getStatus(), savedTask.getStatus(), "Статус задачи не должен изменяться при добавлении в менеджер");
    }

    @Test
    void shouldUpdateTask() {
        Task task = new Task(0, "Test Task", "Test Description", Status.NEW);
        int taskId = taskManager.addTask(task);
        Task updatedTask = new Task(taskId, "Updated Task", "Updated Description", Status.IN_PROGRESS);
        taskManager.updateTask(updatedTask);

        Task savedTask = taskManager.getTask(taskId);
        assertEquals(updatedTask, savedTask, "Task should be updated correctly.");
    }

    @Test
    void shouldRemoveTask() {
        Task task = new Task(0, "Test Task", "Test Description", Status.NEW);
        int taskId = taskManager.addTask(task);
        taskManager.removeTaskById(taskId);

        Task savedTask = taskManager.getTask(taskId);
        assertNull(savedTask, "Task should be removed correctly.");
    }
}
