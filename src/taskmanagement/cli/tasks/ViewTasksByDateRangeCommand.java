package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ViewTasksByDateRangeCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public ViewTasksByDateRangeCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Enter starting date (yyyy-MM-dd): ");
            String startStr = scanner.nextLine().trim();
            System.out.print("Enter end date (yyyy-MM-dd): ");
            String endStr = scanner.nextLine().trim();
            
            LocalDate startDate;
            LocalDate endDate;
            
            try {
                startDate = LocalDate.parse(startStr);
                endDate = LocalDate.parse(endStr);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format. Please use yyyy-MM-dd");
                return;
            }
            
            List<Task> tasks = controller.getTaskCatalog().filterTaskByDueDateRange(startDate, endDate);
            
            if (tasks == null || tasks.isEmpty()) {
                System.out.println("No tasks found in the date range " + startDate + " to " + endDate);
                return;
            }
            
            System.out.println("\n=== Tasks with Due Date between " + startDate + " and " + endDate + " ===");
            displayTasks(tasks);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayTasks(List<Task> tasks) {
        for (Task task : tasks) {
            System.out.println("\nID: " + task.getTaskId());
            System.out.println("Title: " + task.getTitle());
            System.out.println("Due Date: " + task.getDueDate());
            System.out.println("Status: " + task.getStatus());
            System.out.println("Priority: " + task.getPriority());
        }
        System.out.println("\nTotal tasks: " + tasks.size());
    }

    @Override
    public String getName() {
        return "view-tasks-by-date-range";
    }

    @Override
    public String getDescription() {
        return "View all tasks within a date range";
    }
}
