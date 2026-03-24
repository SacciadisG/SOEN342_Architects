package taskmanagement.cli.projects;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Project;
import taskmanagement.domain.Task;
import java.util.Scanner;

public class RemoveTaskFromProjectCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public RemoveTaskFromProjectCommand(SystemController controller, Scanner scanner) {
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
            
            controller.removeTaskFromProject(taskId, projectId);
            String projectName = project.getName();
            System.out.println("Task removed from project successfully.");
            controller.logTaskAction(taskId, "Task removed from project: " + projectName);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "remove-task-from-project";
    }

    @Override
    public String getDescription() {
        return "Remove a task from a project";
    }
}
