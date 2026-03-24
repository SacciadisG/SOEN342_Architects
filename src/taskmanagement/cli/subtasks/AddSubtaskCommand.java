package taskmanagement.cli.subtasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import java.util.Scanner;

public class AddSubtaskCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public AddSubtaskCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Enter task ID: ");
            Long taskId;
            try {
                taskId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Task ID must be a valid number.");
                return;
            }
            
            Task task = controller.getTaskCatalog().findTask(taskId);
            if (task == null) {
                System.out.println("Error: Task with ID " + taskId + " does not exist.");
                return;
            }
            
            System.out.print("Enter subtask title: ");
            String subtaskTitle = scanner.nextLine().trim();
            
            if (subtaskTitle.isEmpty()) {
                System.out.println("Error: Subtask title cannot be empty.");
                return;
            }
            
            controller.addSubtaskToTask(taskId, subtaskTitle);
            System.out.println("Subtask added successfully.");
            controller.logTaskAction(taskId, "Subtask added: " + subtaskTitle);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "add-subtask";
    }

    @Override
    public String getDescription() {
        return "Add a subtask to a task";
    }
}
