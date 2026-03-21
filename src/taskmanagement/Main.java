package taskmanagement;

import taskmanagement.cli.*;
import taskmanagement.controller.SystemController;
import taskmanagement.catalog.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Instantiate catalogs and controller
        TaskCatalog taskCatalog = new TaskCatalog();
        ProjectCatalog projectCatalog = new ProjectCatalog();
        ActivityEntryCatalog activityEntryCatalog = new ActivityEntryCatalog();
        CollaboratorCatalog collaboratorCatalog = new CollaboratorCatalog();
        SystemController controller = new SystemController(taskCatalog, projectCatalog, activityEntryCatalog, collaboratorCatalog);

        // Set up CLI menu and commands
        CLIMenu menu = new CLIMenu(scanner);
        menu.addCommand(new CreateTaskCommand(controller, scanner));
        menu.addCommand(new SearchTasksCommand(controller, scanner));
        menu.addCommand(new ExportTasksCommand(controller, scanner));
        menu.addCommand(new ImportTasksCommand(controller, scanner));
        // Add more commands as needed

        menu.run();
    }
}
