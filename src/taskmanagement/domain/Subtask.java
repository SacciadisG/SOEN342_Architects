package taskmanagement.domain;

import taskmanagement.enums.StatusEnum;

public class Subtask {
    private Long subtaskId;
    private String title;
    private StatusEnum status;

    // Added for Iteration 2: linking a task to a collaborator creates a subtask (per spec)
    private Collaborator assignedCollaborator;

    public Subtask(Long subtaskId, String title, StatusEnum status) {
        this.subtaskId = subtaskId;
        this.title = title;
        this.status = status;
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

    public Collaborator getAssignedCollaborator() {
        return assignedCollaborator;
    }

    public void setAssignedCollaborator(Collaborator assignedCollaborator) {
        this.assignedCollaborator = assignedCollaborator;
    }
}
