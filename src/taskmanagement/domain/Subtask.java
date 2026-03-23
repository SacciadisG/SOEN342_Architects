package taskmanagement.domain;

import taskmanagement.enums.StatusEnum;

public class Subtask {
    private static Long ID = 0L;
    private Long subtaskId;
    private String title;
    private StatusEnum status;

    public Subtask(String title) {
        this.subtaskId = ID++;
        this.title = title;
        this.status = StatusEnum.OPEN;
    }

    public Subtask(String title, StatusEnum status) {
        this.subtaskId = ID++;
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
}
