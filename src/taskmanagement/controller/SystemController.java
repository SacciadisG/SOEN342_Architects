package taskmanagement.controller;

import taskmanagement.catalog.TaskCatalog;
import taskmanagement.catalog.ProjectCatalog;
import taskmanagement.catalog.ActivityEntryCatalog;
import taskmanagement.catalog.CollaboratorCatalog;
import taskmanagement.domain.Project;
import taskmanagement.domain.Task;
import taskmanagement.enums.StatusEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SystemController {
    private TaskCatalog taskCatalog;
    private ProjectCatalog projectCatalog;
    private ActivityEntryCatalog activityEntryCatalog;
    private CollaboratorCatalog collaboratorCatalog;
    private Scanner sc;

    public SystemController(TaskCatalog taskCatalog, ProjectCatalog projectCatalog,
                           ActivityEntryCatalog activityEntryCatalog, CollaboratorCatalog collaboratorCatalog) {
        this.taskCatalog = taskCatalog;
        this.projectCatalog = projectCatalog;
        this.activityEntryCatalog = activityEntryCatalog;
        this.collaboratorCatalog = collaboratorCatalog;
    }

    public void createTask(String details) {
        String[] given = details.split(",");
        if (given.length == 3){
            taskCatalog.addTask(new Task(given[0],given[1],LocalDate.parse(given[2])));
        }
        else if (given.length == 2){
            taskCatalog.addTask(new Task(given[0],given[1]));
        }
        else if (given.length ==1){
            taskCatalog.addTask(new Task(given[0]));
        }

    }

    public void updateTask(Long taskId, Object details) {

    }

    public List<Task> searchTasks(String criteria) {
        if ( criteria.equals("title")){
            System.out.println("give title:\n");
            String title = sc.nextLine();
            return taskCatalog.filterTasksByTitle(title);
        }
        else if (criteria.equals("range")){
            System.out.println("give starting date:\n");
            String start = sc.nextLine();
            System.out.println("give end date:\n");
            String end = sc.nextLine();
            LocalDate s = LocalDate.parse(start);
            LocalDate e = LocalDate.parse(end);
            return taskCatalog.filterTaskByDueDateRange(s,e);
        }
        else if (criteria.equals("status")){
            System.out.println("give status:\n");
            String status = sc.nextLine();
            return taskCatalog.filterTaskByStatus(StatusEnum.valueOf(status));
        }
        else if (criteria.equals("day")){
            System.out.println("give day:\n");
            String day = sc.nextLine();
            return taskCatalog.filterTaskByDayofWeek(day);
        }
        return null;
    }

    public void exportTasksToCSV(String filePath) {
    }

    public void importTasksFromCSV(String filePath) {
    }

    public void addTaskToProject(Long taskId, Long projectId) {

        projectCatalog.findProject(projectId).addTaskToProject(taskCatalog.findTask(taskId));

    }

    public void removeTaskFromProject(Long taskId, Long projectId) {

        projectCatalog.findProject(projectId).removeTaskFromProject(taskCatalog.findTask(taskId));
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
