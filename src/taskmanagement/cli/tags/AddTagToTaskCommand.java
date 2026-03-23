package taskmanagement.cli.tags;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
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
        System.out.println("Enter task ID:");
        Long taskId = Long.parseLong(scanner.nextLine());
        
        System.out.println("Enter tag name:");
        String tagName = scanner.nextLine();
        
        controller.addTagToTask(taskId, tagName);
        System.out.println("Tag '" + tagName + "' added to task successfully.");
        controller.logTaskAction(taskId, "Tag added: " + tagName);
    }

    @Override
    public String getName() {
        return "addtag";
    }

    @Override
    public String getDescription() {
        return "Add a tag to a task";
    }
}
