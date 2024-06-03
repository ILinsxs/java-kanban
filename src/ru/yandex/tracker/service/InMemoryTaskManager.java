package ru.yandex.tracker.service;

import ru.yandex.tracker.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HistoryManager historyManager;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;
    private int idCounter; // Поле-счётчик для генерации уникальных ID

    // Изменил
    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        idCounter = 1;
    }

    // Генерация уникальных ID
    private int generateId() {
        return idCounter++;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getAll();
    }

    @Override
    public List<Task> getAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    // Добавление задач в HashMap, используя ID задачи в качестве ключа
    @Override
    public int addTask(Task task) {
        int id = generateId();
        task.setId(id);
        tasks.put(id, task);
        historyManager.add(task);
        return id;
    }

    @Override
    public int addEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
        historyManager.add(epic);
        return id;
    }

    // При добавлении подзадачи, мы также добавляем её ID в соответствующий Epic
    @Override
    public Subtask addSubtask(Subtask subtask) {
        if (subtask.getEpicId() == subtask.getId()) {
            return null; // Возвращаем null, если подзадача ссылается на свой же эпик
        }
        int id = generateId();
        subtask.setId(id);
        subtasks.put(id, subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtaskId(subtask.getId());
            updateEpicStatus(epic.getId()); // Перерасчёт статуса эпика
        }
        historyManager.add(subtask);
        return subtask;
    }

    // Методы возвращают задачи по идентификатору
    @Override
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    // Методы обновляют существующие задачи
    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
        historyManager.add(task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        historyManager.add(epic);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.getEpicId());
        historyManager.add(subtask);
    }

    // Методы для удаления задач
    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    @Override
    public void removeSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.getSubtaskIds().remove(Integer.valueOf(id));
                updateEpicStatus(epic.getId());
            }
        }
    }

    // Методы удаления всех задач
    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateEpicStatus(epic.getId());
        }
    }

    // Методы возвращают список всех задач
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Subtask> getSubtasksByEpicId(int epicId) {
        List<Subtask> results = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                results.add(subtask);
            }
        }
        return results;
    }

    // Обновление статуса эпика на основе статусов его подзадач
    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return;

        boolean allNew = true;
        boolean allDone = true;

        for (int subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask != null) {
                if (subtask.getStatus() != Status.NEW) {
                    allNew = false;
                }
                if (subtask.getStatus() != Status.DONE) {
                    allDone = false;
                }
            }
        }

        if (allNew) {
            epic.setStatus(Status.NEW);
        } else if (allDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}