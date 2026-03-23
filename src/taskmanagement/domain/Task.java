package taskmanagement.domain;

import taskmanagement.enums.PriorityEnum;
import taskmanagement.enums.StatusEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private Long taskId;
    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private PriorityEnum priority;
    private StatusEnum status;

    // Added for Iteration 2
    private Project project;
    private final List<Subtask> subtasks = new ArrayList<>();
    private final List<Tag> tags = new ArrayList<>();

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
        tags.add(tag);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public Object getTaskProgress() {
        if (subtasks.isEmpty()) return "No subtasks";
        long done = subtasks.stream().filter(s -> s.getStatus() == StatusEnum.COMPLETED).count();
        return done + "/" + subtasks.size() + " subtasks completed";
    }

    // ── Getters and Setters ───────────────────────────────────────────────────

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
