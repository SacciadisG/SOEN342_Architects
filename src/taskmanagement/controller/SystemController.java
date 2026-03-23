package taskmanagement.controller;

import taskmanagement.catalog.TaskCatalog;
import taskmanagement.catalog.ProjectCatalog;
import taskmanagement.catalog.ActivityEntryCatalog;
import taskmanagement.catalog.CollaboratorCatalog;
import taskmanagement.csv.CSVHandler;
import taskmanagement.domain.Task;

import java.io.IOException;
import java.util.List;

public class SystemController {
    private TaskCatalog taskCatalog;
    private ProjectCatalog projectCatalog;
    private ActivityEntryCatalog activityEntryCatalog;
    private CollaboratorCatalog collaboratorCatalog;

    private final CSVHandler csvHandler;

    public SystemController(TaskCatalog taskCatalog, ProjectCatalog projectCatalog,
                           ActivityEntryCatalog activityEntryCatalog, CollaboratorCatalog collaboratorCatalog) {
        this.taskCatalog = taskCatalog;
        this.projectCatalog = projectCatalog;
        this.activityEntryCatalog = activityEntryCatalog;
        this.collaboratorCatalog = collaboratorCatalog;
        this.csvHandler = new CSVHandler(taskCatalog, projectCatalog, collaboratorCatalog);
    }

    public void createTask(Object details) {
    }

    public void updateTask(Long taskId, Object details) {
    }

    public List<Task> searchTasks(Object criteria) {
        return taskCatalog.findTasksByCriteria(criteria);
    }

    public void exportTasksToCSV(String filePath) {
        try {
            csvHandler.exportToCSV(filePath);
        } catch (IOException e) {
            System.err.println("Export failed: " + e.getMessage());
        }
    }

    public void importTasksFromCSV(String filePath) {
        try {
            int count = csvHandler.importFromCSV(filePath);
            System.out.println("Import complete: " + count + " task(s) imported.");
        } catch (IllegalArgumentException e) {
            System.err.println("Validation failed:\n" + e.getMessage());
        } catch (IOException e) {
            System.err.println("Import failed: " + e.getMessage());
        }
    }

    public void addTaskToProject(Long taskId, Long projectId) {
    }

    public void removeTaskFromProject(Long taskId, Long projectId) {
    }

    // Getters and Setters for Catalogs
    public TaskCatalog getTaskCatalog() {
        return taskCatalog;
    }

    public void setTaskCatalog(TaskCatalog taskCatalog) {
        this.taskCatalog = taskCatalog;
    }

    public ProjectCatalog getProjectCatalog() {
        return projectCatalog;
    }

    public void setProjectCatalog(ProjectCatalog projectCatalog) {
        this.projectCatalog = projectCatalog;
    }

    public ActivityEntryCatalog getActivityEntryCatalog() {
        return activityEntryCatalog;
    }

    public void setActivityEntryCatalog(ActivityEntryCatalog activityEntryCatalog) {
        this.activityEntryCatalog = activityEntryCatalog;
    }

    public CollaboratorCatalog getCollaboratorCatalog() {
        return collaboratorCatalog;
    }

    public void setCollaboratorCatalog(CollaboratorCatalog collaboratorCatalog) {
        this.collaboratorCatalog = collaboratorCatalog;
    }
}
