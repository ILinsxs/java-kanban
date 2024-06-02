package ru.yandex.tracker.service;

import ru.yandex.tracker.model.Epic;
import ru.yandex.tracker.model.Subtask;
import ru.yandex.tracker.model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    List<Task> getAll();

    // История просмотра
    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    List<Task> getHistory();

    int addTask(Task task);

    int addEpic(Epic epic);

    Subtask addSubtask(Subtask subtask);

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubtaskById(int id);

    ArrayList<Task> getAllTasks();

    ArrayList<Epic> getAllEpics();

    ArrayList<Subtask> getAllSubtasks();

    ArrayList<Subtask> getSubtasksByEpicId(int epicId);

    //Методы для удаления всех задач
    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();
}
