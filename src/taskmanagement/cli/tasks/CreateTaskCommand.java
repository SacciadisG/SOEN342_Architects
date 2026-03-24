package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class CreateTaskCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public CreateTaskCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter task details: [title, description (optional), due date (optional)]");
        System.out.println("Format: title OR title,description OR title,description,yyyy-MM-dd");
        System.out.print("> ");
        String details = scanner.nextLine().trim();
        
        if (details.isEmpty()) {
            System.out.println("Error: Task title cannot be empty.");
            return;
        }
        
        try {
            Task createdTask = controller.createTask(details);
            if (createdTask != null) {
                System.out.println("Task created successfully with ID: " + createdTask.getTaskId());
                controller.logTaskAction(createdTask.getTaskId(), "Task created");
            } else {
                System.out.println("Failed to create task.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Please use yyyy-MM-dd (e.g., 2026-12-25)");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid input. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Failed to create task. " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "create-task";
    }

    @Override
    public String getDescription() {
        return "Create a new task";
    }
}
