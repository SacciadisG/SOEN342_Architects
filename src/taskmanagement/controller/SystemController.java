package taskmanagement.controller;

import taskmanagement.catalog.TaskCatalog;
import taskmanagement.catalog.ProjectCatalog;
import taskmanagement.catalog.ActivityEntryCatalog;
import taskmanagement.catalog.CollaboratorCatalog;
import taskmanagement.catalog.TagCatalog;
import taskmanagement.domain.Project;
import taskmanagement.domain.Task;
import taskmanagement.domain.Tag;
import taskmanagement.domain.Subtask;
import taskmanagement.domain.ActivityEntry;
import taskmanagement.enums.StatusEnum;
import taskmanagement.enums.PriorityEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SystemController {
    private TaskCatalog taskCatalog;
    private ProjectCatalog projectCatalog;
    private ActivityEntryCatalog activityEntryCatalog;
    private CollaboratorCatalog collaboratorCatalog;
    private TagCatalog tagCatalog;
    private Scanner sc;

    public SystemController(TaskCatalog taskCatalog, ProjectCatalog projectCatalog,
                           ActivityEntryCatalog activityEntryCatalog, CollaboratorCatalog collaboratorCatalog) {
        this.taskCatalog = taskCatalog;
        this.projectCatalog = projectCatalog;
        this.activityEntryCatalog = activityEntryCatalog;
        this.collaboratorCatalog = collaboratorCatalog;
        this.tagCatalog = new TagCatalog();
    }

    public Task createTask(String details) {
        String[] given = details.split(",");
        Task newTask = null;
        if (given.length == 3){
            newTask = new Task(given[0],given[1],LocalDate.parse(given[2]));
            taskCatalog.addTask(newTask);
        }
        else if (given.length == 2){
            newTask = new Task(given[0],given[1]);
            taskCatalog.addTask(newTask);
        }
        else if (given.length ==1){
            newTask = new Task(given[0]);
            taskCatalog.addTask(newTask);
        }
        return newTask;
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

    // Project Management Methods
    public void createProject(String projectName, String projectDescription) {
        Project newProject = new Project(projectName, projectDescription);
        projectCatalog.addProject(newProject);
    }

    public void createProject(String projectName) {
        Project newProject = new Project(projectName);
        projectCatalog.addProject(newProject);
    }

    public void updateProject(Long projectId, String projectName, String projectDescription) {
        Project project = projectCatalog.findProject(projectId);
        if (project != null) {
            project.updateDetails(projectName, projectDescription);
        }
    }

    // Tag Management Methods
    public void addTagToTask(Long taskId, String tagName) {
        Task task = taskCatalog.findTask(taskId);
        if (task != null) {
            Tag tag = tagCatalog.findOrCreateTag(tagName);
            task.addTag(tag);
        }
    }

    public void removeTagFromTask(Long taskId, String tagName) {
        Task task = taskCatalog.findTask(taskId);
        if (task != null) {
            Tag tag = tagCatalog.findTag(tagName);
            if (tag != null) {
                task.removeTag(tag);
            }
        }
    }

    public List<Tag> getAllTags() {
        return tagCatalog.getAllTags();
    }

    // Subtask Management Methods
    public void addSubtaskToTask(Long taskId, String subtaskTitle) {
        Task task = taskCatalog.findTask(taskId);
        if (task != null) {
            Subtask newSubtask = new Subtask(subtaskTitle);
            task.addSubtask(newSubtask);
        }
    }

    public void updateSubtaskStatus(Long taskId, Long subtaskId, StatusEnum status) {
        Task task = taskCatalog.findTask(taskId);
        if (task != null) {
            for (Subtask subtask : task.getSubtasks()) {
                if (subtask.getSubtaskId().equals(subtaskId)) {
                    subtask.updateStatus(status);
                    break;
                }
            }
        }
    }

    public void removeSubtaskFromTask(Long taskId, Long subtaskId) {
        Task task = taskCatalog.findTask(taskId);
        if (task != null) {
            Subtask toRemove = null;
            for (Subtask subtask : task.getSubtasks()) {
                if (subtask.getSubtaskId().equals(subtaskId)) {
                    toRemove = subtask;
                    break;
                }
            }
            if (toRemove != null) {
                task.removeSubtask(toRemove);
            }
        }
    }

    // Activity Logging
    public void logTaskAction(Long taskId, String description) {
        activityEntryCatalog.addActivityEntry(taskId, description);
    }

    public List<ActivityEntry> getActivityHistory(Long taskId) {
        return activityEntryCatalog.getHistoryForTask(taskId);
    }

    // Additional Task Filtering Methods
    public List<Task> filterTasksByTag(Tag tag) {
        return taskCatalog.filterTasksByTag(tag);
    }

    public List<Task> filterTasksByProject(Project project) {
        return taskCatalog.filterTasksByProject(project);
    }

    public List<Task> filterTasksByPriority(PriorityEnum priority) {
        return taskCatalog.filterTasksByPriority(priority);
    }

    public List<Task> searchTasksByKeyword(String keyword) {
        return taskCatalog.searchTasksByKeyword(keyword);
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

    public TagCatalog getTagCatalog() {
        return tagCatalog;
    }

    public void setTagCatalog(TagCatalog tagCatalog) {
        this.tagCatalog = tagCatalog;
    }
}
