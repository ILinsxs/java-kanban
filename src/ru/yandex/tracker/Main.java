package ru.yandex.tracker;

import ru.yandex.tracker.model.Epic;
import ru.yandex.tracker.model.Status;
import ru.yandex.tracker.model.Subtask;
import ru.yandex.tracker.model.Task;
import ru.yandex.tracker.service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task(0, "Задача 1", "Важное описание задачи 1", Status.NEW);
        Task task2 = new Task(0, "Задача 2", "Важное описание задачи 2", Status.NEW);

        Epic epic1 = new Epic(0, "Переезд", "Собрать вещи для переезда");
        Epic epic2 = new Epic(0, "Сделать КТ", "Подготовить материалы к КТ");

        // Добавление задач в менеджер
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        // Добавление эпиков в менеджер
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        Subtask subtask1 = new Subtask(0, "Собрать коробки",
                "Упаковать одежду в контейнеры", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask(0, "Упаковать кошку",
                "Эмоционально подготовить кошку к переезду", Status.NEW, epic1.getId());
        Subtask subtask3 = new Subtask(0, "Контрольная точка",
                "Постепенный сбор материалов к Контрольной точке", Status.NEW, epic2.getId());

        // Добавление подзадач в менеджер
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);

        // Вывод всех задач, эпиков и подзадач
        System.out.println("Все Задачи:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Все Эпики:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }

        System.out.println("Все Подзадачи:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        // Обновление статусов задач и подзадач
        task1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task1);

        task2.setStatus(Status.DONE);
        taskManager.updateTask(task2);

        subtask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);

        subtask2.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask2);

        subtask3.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask3);

        // Проверка обновления статуса эпика после изменения статуса подзадач
        System.out.println("Обновлённые Эпики после изменения статуса подзадач:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }

        // Получение всех подзадач для определенного эпика
        System.out.println("Подзадачи для Epic id=" + epic1.getId() + ":");
        for (Subtask subtask : taskManager.getSubtasksByEpicId(epic1.getId())) {
            System.out.println(subtask);
        }

        // Удаление задачи, эпика и подзадачи
        taskManager.removeTaskById(task1.getId());
        taskManager.removeEpicById(epic1.getId());
        taskManager.removeSubtaskById(subtask1.getId());


        // Вывод всех задач, эпиков и подзадач после удаления
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

        // Удаление всех задач, эпиков и подзадач
        System.out.println("Удаление всех элементов...");
        taskManager.removeAllTasks();
        taskManager.removeAllEpics();
        taskManager.removeAllSubtasks();

        // Вывод всех задач, эпиков и подзадач после удаления всех элементов
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