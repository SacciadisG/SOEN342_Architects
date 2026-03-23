package taskmanagement.domain;

import taskmanagement.enums.PriorityEnum;
import taskmanagement.enums.StatusEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Task {
    private static Long ID = 0L;
    private Long taskId;
    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private PriorityEnum priority;
    private StatusEnum status;
    private Project project;
    private List<Tag> tags;
    private List<Subtask> subtasks;

    public Task(String title, String description, LocalDate dueDate) {
        this.taskId = ID++;
        this.title = title;
        this.description = description;
        creationDate = LocalDate.now();
        this.dueDate = dueDate;
        this.priority = PriorityEnum.MEDIUM;
        this.status = StatusEnum.OPEN;
        this.tags = new ArrayList<>();
        this.subtasks = new ArrayList<>();
    }
    public Task(String title, String description) {
        this.taskId = ID++;
        this.title = title;
        this.description = description;
        creationDate = LocalDate.now();
        this.dueDate = null;
        this.priority = PriorityEnum.MEDIUM;
        this.status = StatusEnum.OPEN;
        this.tags = new ArrayList<>();
        this.subtasks = new ArrayList<>();
    }

    public Task(String title) {
        this.taskId = ID++;
        this.title = title;
        this.description = "";
        creationDate = LocalDate.now();
        this.dueDate = null;
        this.priority = PriorityEnum.MEDIUM;
        this.status = StatusEnum.OPEN;
        this.tags = new ArrayList<>();
        this.subtasks = new ArrayList<>();
    }



    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
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

    public PriorityEnum getPriority() {return priority;}

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public void addTaskToProject(Project project) {
        this.project = project;
    }

    public void removeTaskFromProject() {
        this.project = null;
    }

    public void updateStatus(StatusEnum newStatus) {
        this.status = newStatus;
    }

    public Project getProject() {
        return project;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public void updateDetails(String title, String description, PriorityEnum priority, LocalDate dueDate, StatusEnum status) {
        if (title != null) this.title = title;
        if (description != null) this.description = description;
        if (priority != null) this.priority = priority;
        if (dueDate != null) this.dueDate = dueDate;
        if (status != null) this.status = status;
    }

    public double getTaskProgress() {
        if (subtasks.isEmpty()) {
            return 0.0;
        }
        long completedCount = subtasks.stream()
                .filter(s -> s.getStatus() == StatusEnum.COMPLETED)
                .count();
        return (double) completedCount / subtasks.size() * 100;
    }
}


