package taskmanagement.cli;

import taskmanagement.controller.SystemController;
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
        System.out.println("Enter task details (title, description, priority, due date):");
        // For brevity, just read a line as 'details' placeholder
        String details = scanner.nextLine();
        controller.createTask(details);
        System.out.println("Task created successfully.");
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
