package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.ActivityEntry;

import java.util.List;
import java.util.Scanner;

public class ViewTaskHistoryCommand implements Command {
    private SystemController controller;
    private Scanner scanner;

    public ViewTaskHistoryCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("View Task Activity History");
        System.out.print("Enter task ID: ");
        try {
            Long taskId = Long.parseLong(scanner.nextLine().trim());
            
            if (controller.getTaskCatalog().findTask(taskId) == null) {
                System.out.println("Task not found with ID: " + taskId);
                return;
            }

            List<ActivityEntry> history = controller.getActivityHistory(taskId);
            
            if (history.isEmpty()) {
                System.out.println("No activity history found for task ID: " + taskId);
            } else {
                System.out.println("\n=== Activity History for Task ID: " + taskId + " ===");
                for (ActivityEntry entry : history) {
                    System.out.println("[" + entry.getTimestamp() + "] " + entry.getDescription());
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid task ID. Please enter a valid number.");
        }
    }

    @Override
    public String getName() {
        return "view-history";
    }

    @Override
    public String getDescription() {
        return "View the activity history for a specific task";
    }
}
