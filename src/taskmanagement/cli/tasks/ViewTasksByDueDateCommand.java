package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ViewTasksByDueDateCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public ViewTasksByDueDateCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Enter due date (yyyy-MM-dd): ");
            String dateStr = scanner.nextLine().trim();
            
            LocalDate targetDate;
            try {
                targetDate = LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format. Please use yyyy-MM-dd");
                return;
            }
            
            List<Task> allTasks = controller.getAllTasks();
            
            if (allTasks == null || allTasks.isEmpty()) {
                System.out.println("No tasks found.");
                return;
            }
            
            // Filter tasks with the exact due date
            List<Task> tasksWithDueDate = allTasks.stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().equals(targetDate))
                .toList();
            
            if (tasksWithDueDate.isEmpty()) {
                System.out.println("No tasks found with due date: " + targetDate);
                return;
            }
            
            System.out.println("\n=== Tasks due on " + targetDate + " ===");
            for (Task task : tasksWithDueDate) {
                System.out.println("\nID: " + task.getTaskId());
                System.out.println("Title: " + task.getTitle());
                System.out.println("Status: " + task.getStatus());
                System.out.println("Priority: " + task.getPriority());
            }
            
            System.out.println("\nTotal tasks: " + tasksWithDueDate.size());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "view-tasks-by-due-date";
    }

    @Override
    public String getDescription() {
        return "View tasks with a specific due date";
    }
}
