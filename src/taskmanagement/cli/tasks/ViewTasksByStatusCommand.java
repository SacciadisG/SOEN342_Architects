package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import taskmanagement.enums.StatusEnum;
import java.util.List;
import java.util.Scanner;

public class ViewTasksByStatusCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public ViewTasksByStatusCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.println("""
                Select status:
                1. OPEN
                2. COMPLETED
                3. CANCELLED
                """);
            System.out.print("> ");
            String choice = scanner.nextLine().trim();
            
            StatusEnum status;
            switch(choice) {
                case "1":
                    status = StatusEnum.OPEN;
                    break;
                case "2":
                    status = StatusEnum.COMPLETED;
                    break;
                case "3":
                    status = StatusEnum.CANCELLED;
                    break;
                default:
                    System.out.println("Error: Invalid status selection.");
                    return;
            }
            
            List<Task> tasks = controller.getTaskCatalog().filterTaskByStatus(status);
            
            if (tasks == null || tasks.isEmpty()) {
                System.out.println("No tasks found with status: " + status);
                return;
            }
            
            System.out.println("\n=== Tasks with Status: " + status + " ===");
            displayTasks(tasks);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayTasks(List<Task> tasks) {
        for (Task task : tasks) {
            System.out.println("\nID: " + task.getTaskId());
            System.out.println("Title: " + task.getTitle());
            System.out.println("Status: " + task.getStatus());
            System.out.println("Priority: " + task.getPriority());
            if (task.getDueDate() != null) {
                System.out.println("Due Date: " + task.getDueDate());
            }
        }
        System.out.println("\nTotal tasks: " + tasks.size());
    }

    @Override
    public String getName() {
        return "view-tasks-by-status";
    }

    @Override
    public String getDescription() {
        return "View all tasks filtered by status";
    }
}
