package taskmanagement.catalog;

import taskmanagement.domain.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectCatalog {

    private final List<Project> projects = new ArrayList<>();
    private long nextId = 1L;

    public void addProject(Project project) {
        if (project.getProjectId() == null) {
            project.setProjectId(nextId++);
        }
        projects.add(project);
    }

    public void updateProject(Long projectId, Object details) {
    }

    public Project findProject(Long projectId) {
        return projects.stream()
                .filter(p -> p.getProjectId().equals(projectId))
                .findFirst()
                .orElse(null);
    }

    // Used by CSVHandler for auto-create logic (TSK1.03.03)
    public Project findByName(String name) {
        if (name == null || name.isBlank()) return null;
        return projects.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Project> getAllProjects() {
        return new ArrayList<>(projects);
    }
}
