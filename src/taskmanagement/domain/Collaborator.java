package taskmanagement.domain;

import java.util.List;

public abstract class Collaborator {
    protected Long collaboratorId;
    protected String name;
    protected int maxOpenTasks;

    public Collaborator(Long collaboratorId, String name) {
        this.collaboratorId = collaboratorId;
        this.name = name;
    }

    public List<Task> getAssignedTasks() {
        return null;
    }
    
    // Getters and Setters
    public Long getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(Long collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxOpenTasks() {
        return maxOpenTasks;
    }

    public void setMaxOpenTasks(int maxOpenTasks) {
        this.maxOpenTasks = maxOpenTasks;
    }
}
