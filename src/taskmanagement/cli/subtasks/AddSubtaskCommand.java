package taskmanagement.cli.subtasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
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
        System.out.println("Enter task ID:");
        Long taskId = Long.parseLong(scanner.nextLine());
        
        System.out.println("Enter subtask title:");
        String subtaskTitle = scanner.nextLine();
        
        controller.addSubtaskToTask(taskId, subtaskTitle);
        System.out.println("Subtask added successfully.");
        controller.logTaskAction(taskId, "Subtask added: " + subtaskTitle);
    }

    @Override
    public String getName() {
        return "addsubtask";
    }

    @Override
    public String getDescription() {
        return "Add a subtask to a task";
    }
}
