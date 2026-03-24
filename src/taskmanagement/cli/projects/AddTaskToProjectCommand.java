package taskmanagement.cli.projects;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Project;
import taskmanagement.domain.Task;
import java.util.Scanner;

public class AddTaskToProjectCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public AddTaskToProjectCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Enter task ID: ");
            Long taskId;
            try {
                taskId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Task ID must be a valid number.");
                return;
            }
            
            Task task = controller.getTaskCatalog().findTask(taskId);
            if (task == null) {
                System.out.println("Error: Task with ID " + taskId + " does not exist.");
                return;
            }
            
            System.out.print("Enter project ID: ");
            Long projectId;
            try {
                projectId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Project ID must be a valid number.");
                return;
            }
            
            Project project = controller.getProjectCatalog().findProject(projectId);
            if (project == null) {
                System.out.println("Error: Project with ID " + projectId + " does not exist.");
                return;
            }
            
            controller.addTaskToProject(taskId, projectId);
            String projectName = project.getName();
            System.out.println("Task added to project successfully.");
            controller.logTaskAction(taskId, "Task added to project: " + projectName);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "add-task-to-project";
    }

    @Override
    public String getDescription() {
        return "Add a task to a project";
    }
}
