package ru.yandex.tracker.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtaskIds; // Хранит ID всех подзадач, связанных с Epic

    @Override
    public Epic copy() {
        return new Epic(this);
    }

    public Epic(int id, String title, String description) {
        super(id, title, description, Status.NEW);
        this.subtaskIds = new ArrayList<>();
    }

    public Epic(Epic epic) {
        super(epic);
        this.subtaskIds = new ArrayList<>(epic.subtaskIds);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    // Позволяет добавлять ID подзадач в список
    public void addSubtaskId(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    @Override
    public String toString(){
        return "Epic{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtaskIds=" + subtaskIds +
                '}';
    }
}