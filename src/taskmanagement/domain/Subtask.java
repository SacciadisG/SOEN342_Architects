package taskmanagement.domain;

import taskmanagement.enums.StatusEnum;

public class Subtask {
    private static Long ID = 0L;
    private Long subtaskId;
    private String title;
    private StatusEnum status;
    private Collaborator collaborator;

    public Subtask(String title) {
        this.subtaskId = ID++;
        this.title = title;
        this.status = StatusEnum.OPEN;
        this.collaborator = null;
    }

    public Subtask(String title, StatusEnum status) {
        this.subtaskId = ID++;
        this.title = title;
        this.status = status;
        this.collaborator = null;
    }

    public Subtask(String title, Collaborator collaborator) {
        this.subtaskId = ID++;
        this.title = title;
        this.status = StatusEnum.OPEN;
        this.collaborator = collaborator;
    }

    // Getters and Setters
    public Long getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(Long subtaskId) {
        this.subtaskId = subtaskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public void updateStatus(StatusEnum status) {
        this.status = status;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public boolean hasCollaborator() {
        return collaborator != null;
    }
}
