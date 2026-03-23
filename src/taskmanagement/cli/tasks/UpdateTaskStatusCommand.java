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
        System.out.println("Enter task ID:");
        Long taskId = Long.parseLong(scanner.nextLine());
        
        Task task = controller.getTaskCatalog().findTask(taskId);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }
        
        System.out.println("Current status: " + task.getStatus());
        System.out.println("Enter new status (OPEN, COMPLETED, CANCELLED):");
        String statusStr = scanner.nextLine();
        
        try {
            StatusEnum newStatus = StatusEnum.valueOf(statusStr.toUpperCase());
            task.updateStatus(newStatus);
            controller.logTaskAction(taskId, "Status changed to " + newStatus);
            System.out.println("Task status updated to " + newStatus);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status. Please use: OPEN, COMPLETED, or CANCELLED");
        }
    }

    @Override
    public String getName() {
        return "updatestatus";
    }

    @Override
    public String getDescription() {
        return "Update a task's status (OPEN, COMPLETED, CANCELLED)";
    }
}
