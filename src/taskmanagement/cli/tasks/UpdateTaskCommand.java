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
        System.out.println("Enter task ID to update:");
        Long taskId = Long.parseLong(scanner.nextLine());
        
        Task task = controller.getTaskCatalog().findTask(taskId);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }
        
        System.out.println("Current Task: " + task.getTitle());
        System.out.println("Enter new title (or leave blank to keep current):");
        String newTitle = scanner.nextLine();
        
        System.out.println("Enter new description (or leave blank to keep current):");
        String newDescription = scanner.nextLine();
        
        System.out.println("Enter new priority (LOW, MEDIUM, HIGH) or leave blank:");
        String priorityStr = scanner.nextLine();
        PriorityEnum newPriority = priorityStr.isEmpty() ? null : PriorityEnum.valueOf(priorityStr);
        
        System.out.println("Enter new due date (YYYY-MM-DD) or leave blank:");
        String dueDateStr = scanner.nextLine();
        LocalDate newDueDate = dueDateStr.isEmpty() ? null : LocalDate.parse(dueDateStr);
        
        System.out.println("Enter new status (OPEN, COMPLETED, CANCELLED) or leave blank:");
        String statusStr = scanner.nextLine();
        StatusEnum newStatus = statusStr.isEmpty() ? null : StatusEnum.valueOf(statusStr);
        
        task.updateDetails(newTitle.isEmpty() ? null : newTitle,
                          newDescription.isEmpty() ? null : newDescription,
                          newPriority,
                          newDueDate,
                          newStatus);
        
        System.out.println("Task updated successfully.");
        controller.logTaskAction(taskId, "Task updated - title: " + (newTitle.isEmpty() ? "unchanged" : newTitle) + 
                                         ", priority: " + (newPriority == null ? "unchanged" : newPriority) +
                                         ", due date: " + (newDueDate == null ? "unchanged" : newDueDate));
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "Update an existing task";
    }
}
