package taskmanagement;

import taskmanagement.cli.*;
import taskmanagement.cli.tasks.*;
import taskmanagement.cli.projects.*;
import taskmanagement.cli.tags.*;
import taskmanagement.cli.subtasks.*;
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
        
        // Task commands
        menu.addCommand(new CreateTaskCommand(controller, scanner));
        menu.addCommand(new UpdateTaskCommand(controller, scanner));
        menu.addCommand(new UpdateTaskStatusCommand(controller, scanner));
        menu.addCommand(new SearchTasksCommand(controller, scanner));
        menu.addCommand(new ViewTaskDetailsCommand(controller, scanner));
        menu.addCommand(new ViewTasksByPriorityCommand(controller, scanner));
        menu.addCommand(new SearchTasksByKeywordCommand(controller, scanner));
        menu.addCommand(new ViewTaskHistoryCommand(controller, scanner));
        
        // Tag commands
        menu.addCommand(new AddTagToTaskCommand(controller, scanner));
        menu.addCommand(new ViewTasksByTagCommand(controller, scanner));
        
        // Subtask commands
        menu.addCommand(new AddSubtaskCommand(controller, scanner));
        
        // Project commands
        menu.addCommand(new CreateProjectCommand(controller, scanner));
        menu.addCommand(new AddTaskToProjectCommand(controller, scanner));
        menu.addCommand(new ViewTasksByProjectCommand(controller, scanner));
        
        // Import/Export commands
        menu.addCommand(new ExportTasksCommand(controller, scanner));
        menu.addCommand(new ImportTasksCommand(controller, scanner));
        // Add more commands as needed

        menu.run();
    }
}
