package ru.yandex.tracker;

import ru.yandex.tracker.service.Managers;
import ru.yandex.tracker.service.TaskManager;
import ru.yandex.tracker.model.Task;
import ru.yandex.tracker.model.Status;
import ru.yandex.tracker.model.Epic;
import ru.yandex.tracker.model.Subtask;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task(0 ,"Задача 1", "Важное описание задачи 1", Status.NEW);
        Epic epic1 = new Epic(0, "Переезд", "Собрать вещи для переезда");
        taskManager.addTask(task1);
        taskManager.addEpic(epic1);

        Subtask subtask1 = new Subtask(0, "Собрать коробки", "Упаковать одежду в контейнеры", Status.NEW, epic1.getId());
        taskManager.addSubtask(subtask1);

        printAllTasks(taskManager);

        // Обновление статусов задач и подзадач
        task1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task1);
        taskManager.updateTask(task1);

        subtask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);

        updateEpicStatus(taskManager);

        // Получение всех подзадач для определенного эпика
        System.out.println("Подзадачи для Epic id=" + epic1.getId() + ":");
        for (Subtask subtask : taskManager.getSubtasksByEpicId(epic1.getId())) {
            System.out.println(subtask);
        }

        // Удаление задачи, эпика и подзадачи
        taskManager.removeTaskById(task1.getId());
        taskManager.removeEpicById(epic1.getId());
        taskManager.removeSubtaskById(subtask1.getId());

        printAllTasksAfterRemoval(taskManager);

        removeAllItem(taskManager);

        // Вывод истории просмотров
        System.out.println("История просмотров задач: ");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
    }
    private static void printAllTasks(TaskManager taskManager) {
        System.out.println("Задачи:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Эпики:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
            for (Subtask subtask : taskManager.getSubtasksByEpicId(epic.getId())) {
                System.out.println("--> " + subtask);
            }
        }

        System.out.println("Подзадачи:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
    }

    private static void updateEpicStatus(TaskManager taskManager) {
        System.out.println("Обновлённые Эпики после изменения статуса подзадач:");
        for (Epic epic : taskManager.getAllEpics()) {
            taskManager.getEpic(epic.getId());
            System.out.println(epic);
        }
    }

    private static void printAllTasksAfterRemoval(TaskManager taskManager) {
        System.out.println("Вывод всех задач после удаления:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Вывод всех эпиков после удаления:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }

        System.out.println("Вывод всех подзадач после удаления:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }
    }

    private static void removeAllItem(TaskManager taskManager) {
        System.out.println("Удаление всех элементов...");
        taskManager.removeAllTasks();
        taskManager.removeAllEpics();
        taskManager.removeAllSubtasks();

        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }

        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }

        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println("Удаление всех задач, эпиков и подзадач прошло успешно!)");
    }
}
