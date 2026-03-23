package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import java.util.Scanner;

public class CreateTaskCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public CreateTaskCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter task details:\t [title, description (optional) , due date (optional)]");
        // For brevity, just read a line as 'details' placeholder
        String details = scanner.nextLine();
        Task createdTask = controller.createTask(details);
        if (createdTask != null) {
            System.out.println("Task created successfully with ID: " + createdTask.getTaskId());
            controller.logTaskAction(createdTask.getTaskId(), "Task created");
        } else {
            System.out.println("Failed to create task.");
        }
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a new task";
    }
}
