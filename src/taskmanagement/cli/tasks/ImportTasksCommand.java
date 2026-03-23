package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import java.util.Scanner;

public class ImportTasksCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public ImportTasksCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter file path to import tasks:");
        String filePath = scanner.nextLine();
        controller.importTasksFromCSV(filePath); // IMPLEMENT LOGIC IN CONTROLLER
        System.out.println("Tasks imported from " + filePath);
    }

    @Override
    public String getName() {
        return "import";
    }

    @Override
    public String getDescription() {
        return "Import tasks from CSV";
    }
}
