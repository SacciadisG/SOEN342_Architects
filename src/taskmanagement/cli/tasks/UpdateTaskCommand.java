package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import taskmanagement.enums.PriorityEnum;
import taskmanagement.enums.StatusEnum;

import java.time.LocalDate;
import java.util.Scanner;

public class UpdateTaskCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public UpdateTaskCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Enter task ID to update: ");
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
            
            System.out.println("Current Task: " + task.getTitle());
            System.out.print("Enter new title (or leave blank to keep current): ");
            String newTitle = scanner.nextLine().trim();
            
            System.out.print("Enter new description (or leave blank to keep current): ");
            String newDescription = scanner.nextLine().trim();
            
            System.out.print("Enter new priority (LOW, MEDIUM, HIGH) or leave blank: ");
            String priorityStr = scanner.nextLine().trim();
            PriorityEnum newPriority = null;
            if (!priorityStr.isEmpty()) {
                try {
                    newPriority = PriorityEnum.valueOf(priorityStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: Invalid priority. Use LOW, MEDIUM, or HIGH.");
                    return;
                }
            }
            
            System.out.print("Enter new due date (yyyy-MM-dd) or leave blank: ");
            String dueDateStr = scanner.nextLine().trim();
            LocalDate newDueDate = null;
            if (!dueDateStr.isEmpty()) {
                try {
                    newDueDate = LocalDate.parse(dueDateStr);
                } catch (Exception e) {
                    System.out.println("Error: Invalid date format. Please use yyyy-MM-dd");
                    return;
                }
            }
            
            System.out.print("Enter new status (OPEN, COMPLETED, CANCELLED) or leave blank: ");
            String statusStr = scanner.nextLine().trim();
            StatusEnum newStatus = null;
            if (!statusStr.isEmpty()) {
                try {
                    newStatus = StatusEnum.valueOf(statusStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: Invalid status. Use OPEN, COMPLETED, or CANCELLED.");
                    return;
                }
            }
            
            task.updateDetails(newTitle.isEmpty() ? null : newTitle,
                              newDescription.isEmpty() ? null : newDescription,
                              newPriority,
                              newDueDate,
                              newStatus);
            
            System.out.println("Task updated successfully.");
            controller.logTaskAction(taskId, "Task updated - title: " + (newTitle.isEmpty() ? "unchanged" : newTitle) + 
                                             ", priority: " + (newPriority == null ? "unchanged" : newPriority) +
                                             ", due date: " + (newDueDate == null ? "unchanged" : newDueDate));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "update-task";
    }

    @Override
    public String getDescription() {
        return "Update an existing task";
    }
}
