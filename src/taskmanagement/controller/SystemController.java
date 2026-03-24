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
import taskmanagement.domain.Collaborator;
import taskmanagement.domain.SeniorCollaborator;
import taskmanagement.domain.IntermediateCollaborator;
import taskmanagement.domain.JuniorCollaborator;
import taskmanagement.enums.StatusEnum;
import taskmanagement.enums.PriorityEnum;
import taskmanagement.strategy.RecurrencePattern;
import taskmanagement.strategy.RecurrenceStrategy;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;

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
        try {
            if (given.length == 3){
                String title = given[0].trim();
                String description = given[1].trim();
                String dateStr = given[2].trim();
                LocalDate dueDate = LocalDate.parse(dateStr);
                newTask = new Task(title, description, dueDate);
                taskCatalog.addTask(newTask);
            }
            else if (given.length == 2){
                String title = given[0].trim();
                String description = given[1].trim();
                newTask = new Task(title, description);
                taskCatalog.addTask(newTask);
            }
            else if (given.length == 1){
                String title = given[0].trim();
                if (title.isEmpty()) {
                    throw new IllegalArgumentException("Task title cannot be empty");
                }
                newTask = new Task(title);
                taskCatalog.addTask(newTask);
            }
        } catch (Exception e) {
            throw e;
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
        try {
            StringBuilder csv = new StringBuilder();
            
            // Write header
            csv.append("TaskName,Description,Subtask,Status,Priority,DueDate,ProjectName,ProjectDescription,Collaborator,CollaboratorCategory\n");
            
            // Get all tasks
            List<Task> allTasks = taskCatalog.displayTasksNoFilter();
            
            for (Task task : allTasks) {
                // Get project info if task is in a project
                String projectName = "";
                String projectDescription = "";
                
                for (Project proj : projectCatalog.projects) {
                    if (proj.getTasks().contains(task)) {
                        projectName = escapeCsvField(proj.getName());
                        projectDescription = escapeCsvField(proj.getDescription());
                        break;
                    }
                }
                
                // If task has subtasks, write one row per subtask
                if (!task.getSubtasks().isEmpty()) {
                    for (Subtask subtask : task.getSubtasks()) {
                        String collaboratorName = "";
                        String collaboratorCategory = "";
                        
                        if (subtask.hasCollaborator()) {
                            Collaborator collab = subtask.getCollaborator();
                            collaboratorName = escapeCsvField(collab.getName());
                            collaboratorCategory = getCollaboratorCategory(collab);
                        }
                        
                        String row = escapeCsvField(task.getTitle()) + "," +
                                   escapeCsvField(task.getDescription()) + "," +
                                   escapeCsvField(subtask.getTitle()) + "," +
                                   subtask.getStatus() + "," +
                                   task.getPriority() + "," +
                                   (task.getDueDate() != null ? task.getDueDate().toString() : "") + "," +
                                   projectName + "," +
                                   projectDescription + "," +
                                   collaboratorName + "," +
                                   collaboratorCategory + "\n";
                        csv.append(row);
                    }
                } else {
                    // Task with no subtasks - write one row with empty subtask field
                    String row = escapeCsvField(task.getTitle()) + "," +
                               escapeCsvField(task.getDescription()) + "," +
                               "" + "," +
                               task.getStatus() + "," +
                               task.getPriority() + "," +
                               (task.getDueDate() != null ? task.getDueDate().toString() : "") + "," +
                               projectName + "," +
                               projectDescription + "," +
                               "" + "," +
                               "" + "\n";
                    csv.append(row);
                }
            }
            
            // Write to file
            Files.write(Paths.get(filePath), csv.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Tasks exported successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("Error exporting tasks: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error exporting tasks: " + e.getMessage());
        }
    }

    public void importTasksFromCSV(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            if (lines.isEmpty()) {
                System.out.println("CSV file is empty");
                return;
            }
            
            // Skip header line
            HashMap<String, Project> projectsByName = new HashMap<>();
            
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) {
                    continue;
                }
                
                try {
                    List<String> fields = parseCSVLine(line);
                    if (fields.size() < 10) {
                        System.out.println("Warning: Line " + (i + 1) + " has insufficient fields, skipping");
                        continue;
                    }
                    
                    String taskName = fields.get(0).trim();
                    String description = fields.get(1).trim();
                    String subtaskTitle = fields.get(2).trim();
                    String status = fields.get(3).trim();
                    String priority = fields.get(4).trim();
                    String dueDate = fields.get(5).trim();
                    String projectName = fields.get(6).trim();
                    String projectDescription = fields.get(7).trim();
                    String collaboratorName = fields.get(8).trim();
                    String collaboratorCategory = fields.get(9).trim();
                    
                    // Create or get project
                    Project project = null;
                    if (!projectName.isEmpty()) {
                        if (projectsByName.containsKey(projectName)) {
                            project = projectsByName.get(projectName);
                        } else {
                            project = new Project(projectName, projectDescription);
                            projectCatalog.addProject(project);
                            projectsByName.put(projectName, project);
                        }
                    }
                    
                    // Create or get task
                    Task task = null;
                    LocalDate taskDueDate = null;
                    if (!dueDate.isEmpty()) {
                        try {
                            taskDueDate = LocalDate.parse(dueDate);
                        } catch (Exception e) {
                            System.out.println("Warning: Invalid date format at line " + (i + 1) + ": " + dueDate);
                        }
                    }
                    
                    // Check if task already exists with same name in catalog
                    List<Task> allTasks = taskCatalog.displayTasksNoFilter();
                    boolean taskExists = false;
                    for (Task existingTask : allTasks) {
                        if (existingTask.getTitle().equals(taskName)) {
                            task = existingTask;
                            taskExists = true;
                            break;
                        }
                    }
                    
                    if (!taskExists) {
                        task = new Task(taskName, description, taskDueDate);
                        taskCatalog.addTask(task);
                    }
                    
                    // Add task to project if specified
                    if (project != null && !project.getTasks().contains(task)) {
                        project.addTaskToProject(task);
                    }
                    
                    // Set task priority if provided
                    if (!priority.isEmpty()) {
                        try {
                            task.setPriority(PriorityEnum.valueOf(priority));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Warning: Invalid priority at line " + (i + 1) + ": " + priority);
                        }
                    }
                    
                    // Set task status if provided
                    if (!status.isEmpty()) {
                        try {
                            task.updateStatus(StatusEnum.valueOf(status));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Warning: Invalid status at line " + (i + 1) + ": " + status);
                        }
                    }
                    
                    // Create subtask if specified
                    if (!subtaskTitle.isEmpty()) {
                        Collaborator collaborator = null;
                        
                        // Create or get collaborator if specified
                        if (!collaboratorName.isEmpty() && !collaboratorCategory.isEmpty()) {
                            // Find collaborator by name
                            List<Collaborator> allCollaborators = collaboratorCatalog.getAllCollaborators();
                            for (Collaborator collab : allCollaborators) {
                                if (collab.getName().equals(collaboratorName)) {
                                    collaborator = collab;
                                    break;
                                }
                            }
                            
                            // Create collaborator if not found
                            if (collaborator == null) {
                                collaborator = createCollaborator(collaboratorName, collaboratorCategory);
                            }
                        }
                        
                        // Check if subtask already exists
                        boolean subtaskExists = false;
                        for (Subtask existingSubtask : task.getSubtasks()) {
                            if (existingSubtask.getTitle().equals(subtaskTitle)) {
                                subtaskExists = true;
                                if (collaborator != null && !existingSubtask.hasCollaborator()) {
                                    existingSubtask.setCollaborator(collaborator);
                                }
                                break;
                            }
                        }
                        
                        // Create new subtask if it doesn't exist
                        if (!subtaskExists) {
                            if (collaborator != null) {
                                Subtask newSubtask = new Subtask(subtaskTitle, collaborator);
                                task.addSubtask(newSubtask);
                            } else {
                                Subtask newSubtask = new Subtask(subtaskTitle);
                                task.addSubtask(newSubtask);
                            }
                        }
                    }
                    
                    logTaskAction(task.getTaskId(), "Imported from CSV");
                    
                } catch (Exception e) {
                    System.out.println("Error processing line " + (i + 1) + ": " + e.getMessage());
                }
            }
            
            System.out.println("Tasks imported successfully from " + filePath);
        } catch (IOException e) {
            System.out.println("Error importing tasks: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error importing tasks: " + e.getMessage());
        }
    }

    /**
     * Escape CSV field by wrapping in quotes and escaping internal quotes
     */
    private String escapeCsvField(String field) {
        if (field == null || field.isEmpty()) {
            return "";
        }
        // If field contains comma, quote, or newline, wrap in quotes and escape quotes
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }

    /**
     * Get collaborator category name from collaborator instance
     */
    private String getCollaboratorCategory(Collaborator collaborator) {
        if (collaborator instanceof SeniorCollaborator) {
            return "senior";
        } else if (collaborator instanceof IntermediateCollaborator) {
            return "intermediate";
        } else if (collaborator instanceof JuniorCollaborator) {
            return "junior";
        }
        return "";
    }

    /**
     * Parse CSV line handling quoted fields
     */
    private List<String> parseCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean insideQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // Escaped quote
                    field.append('"');
                    i++; // Skip next quote
                } else {
                    // Toggle quote state
                    insideQuotes = !insideQuotes;
                }
            } else if (c == ',' && !insideQuotes) {
                // Field separator
                fields.add(field.toString());
                field = new StringBuilder();
            } else {
                field.append(c);
            }
        }
        
        fields.add(field.toString());
        return fields;
    }

    public void addTaskToProject(Long taskId, Long projectId) {

        projectCatalog.findProject(projectId).addTaskToProject(taskCatalog.findTask(taskId));

    }

    public void removeTaskFromProject(Long taskId, Long projectId) {
        projectCatalog.findProject(projectId).removeTaskFromProject(taskCatalog.findTask(taskId));
    }

    // Project Management Methods
    public Project createProject(String projectName, String projectDescription) {
        Project newProject = new Project(projectName, projectDescription);
        projectCatalog.addProject(newProject);
        return newProject;
    }

    public Project createProject(String projectName) {
        Project newProject = new Project(projectName);
        projectCatalog.addProject(newProject);
        return newProject;
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

    public List<Task> getAllTasks() {
        return taskCatalog.displayTasksNoFilter();
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

    // Recurring Task Management Methods
    public Task createRecurringTask(String title, String description, LocalDate startDate, LocalDate endDate, RecurrenceStrategy strategy) {
        Task newTask = new Task(title, description, startDate);
        RecurrencePattern pattern = new RecurrencePattern(strategy, startDate, endDate);
        newTask.setRecurrencePattern(pattern);
        newTask.setLastGeneratedDate(startDate.minusDays(1)); // Set to day before start so first occurrence generates on startDate
        taskCatalog.addTask(newTask);
        return newTask;
    }

    public void generateOccurrencesIfNeeded(Long recurringTaskId) {
        Task recurringTask = taskCatalog.findTask(recurringTaskId);
        if (recurringTask == null || !recurringTask.isRecurring()) {
            return;
        }

        RecurrencePattern pattern = recurringTask.getRecurrencePattern();
        LocalDate lastGenerated = recurringTask.getLastGeneratedDate();
        LocalDate today = LocalDate.now();

        // Generate all occurrences from lastGeneratedDate up to today
        LocalDate nextOccurrenceDate = pattern.getNextOccurrenceDate(lastGenerated);
        while (nextOccurrenceDate != null && !nextOccurrenceDate.isAfter(today)) {
            // Create occurrence task
            Task occurrence = new Task(recurringTask.getTitle(), recurringTask.getDescription(), nextOccurrenceDate);
            taskCatalog.addTask(occurrence);
            
            // Log the activity
            logTaskAction(recurringTaskId, "Generated occurrence on " + nextOccurrenceDate);

            // Move to next
            lastGenerated = nextOccurrenceDate;
            nextOccurrenceDate = pattern.getNextOccurrenceDate(lastGenerated);
        }

        // Update lastGeneratedDate if we generated any occurrences
        if (!lastGenerated.equals(recurringTask.getLastGeneratedDate())) {
            recurringTask.setLastGeneratedDate(lastGenerated);
        }
    }

    // Collaborator Management Methods
    public Collaborator createCollaborator(String name, String type) {
        Long collaboratorId = collaboratorCatalog.getNextCollaboratorId();
        Collaborator newCollaborator;
        switch (type.toLowerCase()) {
            case "senior":
                newCollaborator = new SeniorCollaborator(collaboratorId, name);
                break;
            case "intermediate":
                newCollaborator = new IntermediateCollaborator(collaboratorId, name);
                break;
            case "junior":
                newCollaborator = new JuniorCollaborator(collaboratorId, name);
                break;
            default:
                throw new IllegalArgumentException("Invalid collaborator type: " + type);
        }
        collaboratorCatalog.addCollaborator(newCollaborator);
        return newCollaborator;
    }

    public void addCollaboratorToTask(Long taskId, Long collaboratorId) {
        Task task = taskCatalog.findTask(taskId);
        Collaborator collaborator = collaboratorCatalog.findCollaborator(collaboratorId);

        if (task == null) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }
        if (collaborator == null) {
            throw new IllegalArgumentException("Collaborator with ID " + collaboratorId + " not found");
        }

        // Check if collaborator can be assigned
        if (!collaboratorCatalog.canAssignTask(collaborator)) {
            throw new IllegalArgumentException("Collaborator " + collaborator.getName() + " has reached maximum open tasks");
        }

        // Create a subtask for this collaborator assignment
        Subtask subtask = new Subtask(task.getTitle() + " - " + collaborator.getName(), collaborator);
        task.addSubtask(subtask);

        // Log the activity
        logTaskAction(taskId, "Assigned to collaborator " + collaborator.getName());
    }

    public void addCollaboratorToProject(Long projectId, Long collaboratorId) {
        Project project = projectCatalog.findProject(projectId);
        Collaborator collaborator = collaboratorCatalog.findCollaborator(collaboratorId);

        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " not found");
        }
        if (collaborator == null) {
            throw new IllegalArgumentException("Collaborator with ID " + collaboratorId + " not found");
        }

        // Add collaborator to project (implicit through project's task assignments)
        // This is handled at task level when adding collaborator to task in the project
    }
}
