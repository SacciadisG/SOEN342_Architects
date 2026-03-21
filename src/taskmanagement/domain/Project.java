package taskmanagement.domain;

public class Project {
    private Long projectId;
    private String name;
    private String description;

    public Project(Long projectId, String name, String description) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
    }

    public void addTaskToProject(Task task) {
    }

    public void removeTaskFromProject(Task task) {
    }

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
