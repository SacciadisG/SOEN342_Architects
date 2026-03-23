package taskmanagement.cli.projects;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Project;
import taskmanagement.domain.Task;

import java.util.List;
import java.util.Scanner;

public class ViewTasksByProjectCommand implements Command {
    private SystemController controller;
    private Scanner scanner;

    public ViewTasksByProjectCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("View Tasks by Project");
        System.out.print("Enter project ID: ");
        try {
            Long projectId = Long.parseLong(scanner.nextLine().trim());
            Project project = controller.getProjectCatalog().findProject(projectId);
            
            if (project == null) {
                System.out.println("Project not found with ID: " + projectId);
                return;
            }

            List<Task> tasks = controller.filterTasksByProject(project);
            
            if (tasks.isEmpty()) {
                System.out.println("No tasks found in project: " + project.getName());
            } else {
                System.out.println("\n=== Tasks in Project: " + project.getName() + " ===");
                for (Task task : tasks) {
                    System.out.println("ID: " + task.getTaskId() + " | Title: " + task.getTitle() + 
                                     " | Status: " + task.getStatus() + " | Due: " + task.getDueDate());
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid project ID. Please enter a valid number.");
        }
    }

    @Override
    public String getName() {
        return "view-by-project";
    }

    @Override
    public String getDescription() {
        return "View all tasks in a specific project";
    }
}
