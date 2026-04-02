package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;

import java.util.List;
import java.util.Scanner;

public class ExportTaskToCalendarCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public ExportTaskToCalendarCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Export Task to Calendar (iCalendar Format)");
        System.out.println("What would you like to export?");
        System.out.println("1. A single task");
        System.out.println("2. All tasks in a project");
        System.out.println("3. Filtered tasks (by status, priority, or date range)");
        System.out.print("Enter choice (1-3): ");
        
        String choice = scanner.nextLine().trim();
        System.out.print("Enter output file path (e.g., tasks.ics): ");
        String filePath = scanner.nextLine().trim();

        try {
            switch (choice) {
                case "1":
                    exportSingleTask(filePath);
                    break;
                case "2":
                    exportProjectTasks(filePath);
                    break;
                case "3":
                    exportFilteredTasks(filePath);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Error during export: " + e.getMessage());
        }
    }

    private void exportSingleTask(String filePath) {
        System.out.print("Enter task ID: ");
        Long taskId = Long.parseLong(scanner.nextLine().trim());
        
        Task task = controller.getTaskCatalog().findTask(taskId);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }
        if (task.getDueDate() == null) {
            System.out.println("Task must have a due date to export to calendar.");
            return;
        }

        controller.exportTaskToCalendar(taskId, filePath);
    }

    private void exportProjectTasks(String filePath) {
        System.out.print("Enter project ID: ");
        Long projectId = Long.parseLong(scanner.nextLine().trim());
        
        controller.exportProjectTasksToCalendar(projectId, filePath);
    }

    private void exportFilteredTasks(String filePath) {
        System.out.println("Filter by: 1=Status, 2=Priority, 3=Date Range, 4=Tag");
        System.out.print("Enter filter type (1-4): ");
        String filterType = scanner.nextLine().trim();

        List<Task> filteredTasks = null;

        switch (filterType) {
            case "1":
                System.out.println("Select status: OPEN, COMPLETED, CANCELLED");
                System.out.print("Enter status: ");
                String statusStr = scanner.nextLine().trim().toUpperCase();
                filteredTasks = controller.searchTasks("status=" + statusStr).stream()
                    .filter(t -> t.getStatus().toString().equals(statusStr))
                    .toList();
                break;
            case "2":
                System.out.println("Select priority: LOW, MEDIUM, HIGH");
                System.out.print("Enter priority: ");
                String priorityStr = scanner.nextLine().trim().toUpperCase();
                try {
                    filteredTasks = controller.filterTasksByPriority(
                        taskmanagement.enums.PriorityEnum.valueOf(priorityStr));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid priority.");
                    return;
                }
                break;
            case "3":
                System.out.print("Enter start date (YYYY-MM-DD): ");
                String startStr = scanner.nextLine().trim();
                System.out.print("Enter end date (YYYY-MM-DD): ");
                String endStr = scanner.nextLine().trim();
                try {
                    java.time.LocalDate start = java.time.LocalDate.parse(startStr);
                    java.time.LocalDate end = java.time.LocalDate.parse(endStr);
                    filteredTasks = controller.getTaskCatalog().filterTaskByDueDateRange(start, end);
                } catch (Exception e) {
                    System.out.println("Invalid date format.");
                    return;
                }
                break;
            case "4":
                System.out.print("Enter tag name: ");
                String tagName = scanner.nextLine().trim();
                taskmanagement.domain.Tag tag = controller.getTagCatalog().findTag(tagName);
                if (tag == null) {
                    System.out.println("Tag not found.");
                    return;
                }
                filteredTasks = controller.filterTasksByTag(tag);
                break;
            default:
                System.out.println("Invalid filter type.");
                return;
        }

        if (filteredTasks == null || filteredTasks.isEmpty()) {
            System.out.println("No tasks match the filter criteria.");
            return;
        }

        controller.exportFilteredTasksToCalendar(filteredTasks, filePath);
    }

    @Override
    public String getName() {
        return "export-calendar";
    }

    @Override
    public String getDescription() {
        return "Export tasks to iCalendar format (.ics file)";
    }
}
