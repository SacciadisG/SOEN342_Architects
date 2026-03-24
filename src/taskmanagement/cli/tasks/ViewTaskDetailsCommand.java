package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import taskmanagement.domain.Subtask;
import taskmanagement.domain.Tag;

import java.util.Scanner;

public class ViewTaskDetailsCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public ViewTaskDetailsCommand(SystemController controller, Scanner scanner) {
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
                System.out.println("Error: Task with ID " + taskId + " not found.");
                return;
            }
            
            System.out.println("\n=== Task Details ===");
            System.out.println("ID: " + task.getTaskId());
            System.out.println("Title: " + task.getTitle());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Priority: " + task.getPriority());
            System.out.println("Status: " + task.getStatus());
            System.out.println("Created: " + task.getCreationDate());
            System.out.println("Due Date: " + (task.getDueDate() != null ? task.getDueDate() : "None"));
            
            if (task.getProject() != null) {
                System.out.println("Project: " + task.getProject().getName());
            } else {
                System.out.println("Project: Not assigned");
            }
            
            System.out.println("\nTags: ");
            if (task.getTags().isEmpty()) {
                System.out.println("  No tags assigned");
            } else {
                for (Tag tag : task.getTags()) {
                    System.out.println("  - " + tag.getName());
                }
            }
            
            System.out.println("\nSubtasks (" + task.getSubtasks().size() + "):");
            if (task.getSubtasks().isEmpty()) {
                System.out.println("  No subtasks");
            } else {
                for (Subtask subtask : task.getSubtasks()) {
                    System.out.println("  - " + subtask.getTitle() + " (" + subtask.getStatus() + ")");
                }
            }
            
            System.out.println("\nProgress: " + String.format("%.1f", task.getTaskProgress()) + "%");
            System.out.println("====================\n");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "view-task";
    }

    @Override
    public String getDescription() {
        return "View detailed task information";
    }
}
