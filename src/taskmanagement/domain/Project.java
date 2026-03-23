package taskmanagement.domain;

import java.util.List;

public class Project {
    private static long Ids = 1;
    private Long projectId;
    private String name;
    private String description;
    private List<Task> tasks;

    public Project(Long projectId, String name, String description) {
        this.projectId = Ids++;
        this.name = name;
        this.description = description;
    }

    public void addTaskToProject(Task task) {
        tasks.add(task);
    }

    public void removeTaskFromProject(Task task) {tasks.remove(task);}

    public void addCollaboratorToProject(Collaborator collaborator) {

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
}
