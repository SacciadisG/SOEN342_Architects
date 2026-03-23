package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import taskmanagement.enums.PriorityEnum;

import java.util.List;
import java.util.Scanner;

public class ViewTasksByPriorityCommand implements Command {
    private SystemController controller;
    private Scanner scanner;

    public ViewTasksByPriorityCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("View Tasks by Priority");
        System.out.println("Available priorities: LOW, MEDIUM, HIGH");
        System.out.print("Enter priority: ");
        String priorityStr = scanner.nextLine().trim().toUpperCase();
        
        try {
            PriorityEnum priority = PriorityEnum.valueOf(priorityStr);
            List<Task> tasks = controller.filterTasksByPriority(priority);
            
            if (tasks.isEmpty()) {
                System.out.println("No tasks found with priority: " + priority);
            } else {
                System.out.println("\n=== Tasks with Priority: " + priority + " ===");
                for (Task task : tasks) {
                    System.out.println("ID: " + task.getTaskId() + " | Title: " + task.getTitle() + 
                                     " | Status: " + task.getStatus() + " | Due: " + task.getDueDate());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid priority. Please use LOW, MEDIUM, or HIGH.");
        }
    }

    @Override
    public String getName() {
        return "view-by-priority";
    }

    @Override
    public String getDescription() {
        return "View all tasks filtered by priority level";
    }
}
