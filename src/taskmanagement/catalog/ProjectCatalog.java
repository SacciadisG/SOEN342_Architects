package taskmanagement.catalog;

import taskmanagement.domain.Project;

import java.util.List;
import java.util.ArrayList;

public class ProjectCatalog {
    public List<Project> projects = new ArrayList<>();
    
    public void addProject(Project project) {
        // Check if project name already exists
        boolean exists = false;
        for (Project p : projects) {
            if (p.getName().equals(project.getName())) {
                exists = true;
                break;
            }
        }
        
        if (!exists) {
            projects.add(project);
        } else {
            System.out.println("Project with name '" + project.getName() + "' already exists. Project not added.");
        }
    }

    public void updateProject(Long projectId, Object details) {
        Project project = findProject(projectId);
        if (project != null) {
            // details can be a string with format "name,description"
            if (details instanceof String) {
                String[] parts = ((String) details).split(",", 2);
                if (parts.length >= 1 && !parts[0].isEmpty()) {
                    project.setName(parts[0]);
                }
                if (parts.length >= 2 && !parts[1].isEmpty()) {
                    project.setDescription(parts[1]);
                }
            }
        }
    }

    public void updateProjectName(Long projectId, String name) {
        Project p = findProject(projectId);
        if (p != null) {
            p.setName(name);
        }
    }

    public void updateProjectDescription(Long projectId, String desc) {
        Project p = findProject(projectId);
        if (p != null) {
            p.setDescription(desc);
        }
    }

    public Project findProject(Long projectId) {
        Project pr = null;
        for (Project p : projects) {
            if (p.getProjectId() == projectId) {
                pr = p;
                break;
            }
        }
        return pr;
    }

    public Project findProjectByName(String name) {
        for (Project p : projects) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public void deleteProject(Long projectId) {
        projects.removeIf(p -> p.getProjectId() == projectId);
    }
}
