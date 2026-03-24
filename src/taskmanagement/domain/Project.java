package taskmanagement.domain;

import java.util.List;
import java.util.ArrayList;

public class Project {
    private static Long ID = 0L;
    private Long projectId;
    private String name;
    private String description;
    private List<Task> tasks;

    public Project(String name, String description) {
        this.projectId = ID++;
        this.name = name;
        this.description = description;
        this.tasks = new ArrayList<>();
    }

    public Project(String name) {
        this.projectId = ID++;
        this.name = name;
        this.description = "";
        this.tasks = new ArrayList<>();
    }

    public void addTaskToProject(Task task) {
        if (!tasks.contains(task)) {
            task.addTaskToProject(this);
            tasks.add(task);
        }
    }

    public void removeTaskFromProject(Task task) {
        if (tasks.contains(task)) {
            task.removeTaskFromProject();
            tasks.remove(task);
        }
    }

    public void addCollaboratorToProject(Collaborator collaborator) {
        // IMPLEMENT LOGIC SOON
    }
    
    // Getters and Setters
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void updateDetails(String name, String description) {
        if (name != null) this.name = name;
        if (description != null) this.description = description;
    }
}
