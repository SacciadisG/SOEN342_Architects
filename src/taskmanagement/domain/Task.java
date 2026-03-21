package taskmanagement.domain;

import taskmanagement.enums.PriorityEnum;
import taskmanagement.enums.StatusEnum;

import java.time.LocalDate;
import java.util.List;

public class Task {
    private Long taskId;
    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private PriorityEnum priority;
    private StatusEnum status;

    public Task(Long taskId, String title, String description, LocalDate creationDate, 
                LocalDate dueDate, PriorityEnum priority, StatusEnum status) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    public void updateDetails(Object details) {
    }

    public void addTag(Tag tag) {
    }

    public void addSubtask(Subtask subtask) {
    }

    public Object getTaskProgress() {
        return null;
    }
    
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
