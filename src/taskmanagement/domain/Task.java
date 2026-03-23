package taskmanagement.domain;

import taskmanagement.enums.PriorityEnum;
import taskmanagement.enums.StatusEnum;

import java.time.LocalDate;
import java.util.List;

public class Task {
    private static Long ID;
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
    }
    public Task(String title, String description) {
        this.taskId = ID++;
        this.title = title;
        this.description = description;
        creationDate = LocalDate.now();
        this.dueDate = null;
        this.priority = PriorityEnum.MEDIUM;
        this.status = StatusEnum.OPEN;
    }

    public Task(String title) {
        this.taskId = ID++;
        this.title = title;
        this.description = "";
        creationDate = LocalDate.now();
        this.dueDate = null;
        this.priority = PriorityEnum.MEDIUM;
        this.status = StatusEnum.OPEN;
    }



    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
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

    public void connectToProject(Project project){this.project = project;}

    public void disconnectFromProject(){this.project = null;}
}


