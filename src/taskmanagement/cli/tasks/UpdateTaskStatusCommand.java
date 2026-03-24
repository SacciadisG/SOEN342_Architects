package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import taskmanagement.enums.StatusEnum;

import java.util.Scanner;

public class UpdateTaskStatusCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public UpdateTaskStatusCommand(SystemController controller, Scanner scanner) {
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
            
            System.out.println("Current status: " + task.getStatus());
            System.out.print("Enter new status (OPEN, COMPLETED, CANCELLED): ");
            String statusStr = scanner.nextLine().trim();
            
            try {
                StatusEnum newStatus = StatusEnum.valueOf(statusStr.toUpperCase());
                task.updateStatus(newStatus);
                controller.logTaskAction(taskId, "Status changed to " + newStatus);
                System.out.println("Task status updated to " + newStatus);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid status. Please use: OPEN, COMPLETED, or CANCELLED");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "update-task-status";
    }

    @Override
    public String getDescription() {
        return "Update a task's status (OPEN, COMPLETED, CANCELLED)";
    }
}
