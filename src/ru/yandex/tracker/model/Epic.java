package ru.yandex.tracker.model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds; // Хранит ID всех подзадач, связанных с Epic

    public Epic(int id, String title, String description) {
        super(id, title, description, Status.NEW);
        this.subtaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtaskIds() {
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