package taskmanagement.cli.tags;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import java.util.Scanner;

public class AddTagToTaskCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public AddTagToTaskCommand(SystemController controller, Scanner scanner) {
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
            
            System.out.print("Enter tag name: ");
            String tagName = scanner.nextLine().trim();
            
            if (tagName.isEmpty()) {
                System.out.println("Error: Tag name cannot be empty.");
                return;
            }
            
            controller.addTagToTask(taskId, tagName);
            System.out.println("Tag '" + tagName + "' added to task successfully.");
            controller.logTaskAction(taskId, "Tag added: " + tagName);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "add-tag-to-task";
    }

    @Override
    public String getDescription() {
        return "Add a tag to a task";
    }
}
