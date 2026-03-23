package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import java.util.Scanner;

public class ExportTasksCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public ExportTasksCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter file path to export tasks:");
        String filePath = scanner.nextLine();
        controller.exportTasksToCSV(filePath); // IMPLEMENT LOGIC IN CONTROLLER
        System.out.println("Tasks exported to " + filePath);
    }

    @Override
    public String getName() {
        return "export";
    }

    @Override
    public String getDescription() {
        return "Export tasks to CSV";
    }
}
