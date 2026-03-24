package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import taskmanagement.enums.StatusEnum;

public class SearchTasksCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public SearchTasksCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("""
                Enter search criteria:
                (options are "title", "range", "status", "day")
                """);
        System.out.print("> ");
        String criteria = scanner.nextLine().trim().toLowerCase();
        
        List<Task> results = null;
        
        try {
            switch(criteria) {
                case "title":
                    System.out.print("Enter title to search: ");
                    String title = scanner.nextLine().trim();
                    if (title.isEmpty()) {
                        System.out.println("Error: Title cannot be empty.");
                        return;
                    }
                    results = controller.getTaskCatalog().filterTasksByTitle(title);
                    break;
                    
                case "range":
                    System.out.print("Enter starting date (yyyy-MM-dd): ");
                    String startStr = scanner.nextLine().trim();
                    System.out.print("Enter end date (yyyy-MM-dd): ");
                    String endStr = scanner.nextLine().trim();
                    try {
                        LocalDate start = LocalDate.parse(startStr);
                        LocalDate end = LocalDate.parse(endStr);
                        results = controller.getTaskCatalog().filterTaskByDueDateRange(start, end);
                    } catch (DateTimeParseException e) {
                        System.out.println("Error: Invalid date format. Please use yyyy-MM-dd");
                        return;
                    }
                    break;
                    
                case "status":
                    System.out.print("Enter status (PENDING, IN_PROGRESS, COMPLETED): ");
                    String statusStr = scanner.nextLine().trim().toUpperCase();
                    try {
                        StatusEnum status = StatusEnum.valueOf(statusStr);
                        results = controller.getTaskCatalog().filterTaskByStatus(status);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: Invalid status. Use PENDING, IN_PROGRESS, or COMPLETED");
                        return;
                    }
                    break;
                    
                case "day":
                    System.out.print("Enter day of week (MONDAY, TUESDAY, etc.): ");
                    String day = scanner.nextLine().trim();
                    results = controller.getTaskCatalog().filterTaskByDayofWeek(day);
                    break;
                    
                default:
                    System.out.println("Error: Invalid criteria. Use 'title', 'range', 'status', or 'day'");
                    return;
            }
            
            System.out.println("\nSearch Results:");
            if (results == null || results.isEmpty()) {
                System.out.println("No tasks found.");
            } else {
                for (Task task : results) {
                    System.out.println("- " + task.getTitle() + " (ID: " + task.getTaskId() + ")");
                }
            }
        } catch (Exception e) {
            System.out.println("Error during search: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "search-tasks";
    }

    @Override
    public String getDescription() {
        return "Search tasks by criteria (title, date range, status, or day)";
    }
}
