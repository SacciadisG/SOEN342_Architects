package taskmanagement;

import taskmanagement.cli.*;
import taskmanagement.cli.tasks.*;
import taskmanagement.cli.projects.*;
import taskmanagement.cli.tags.*;
import taskmanagement.cli.subtasks.*;
import taskmanagement.cli.collaborators.*;
import taskmanagement.cli.recurring_tasks.*;
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
        CollaboratorCatalog collaboratorCatalog = new CollaboratorCatalog(taskCatalog);
        SystemController controller = new SystemController(taskCatalog, projectCatalog, activityEntryCatalog, collaboratorCatalog);

        // Set up CLI menu and commands
        CLIMenu menu = new CLIMenu(scanner);
        
        // Task commands
        menu.addCommand(new CreateTaskCommand(controller, scanner));
        menu.addCommand(new UpdateTaskCommand(controller, scanner));
        menu.addCommand(new UpdateTaskStatusCommand(controller, scanner));
        menu.addCommand(new ViewTaskDetailsCommand(controller, scanner));
        menu.addCommand(new ViewTasksByPriorityCommand(controller, scanner));
        menu.addCommand(new ViewAllTasksCommand(controller, scanner));
        menu.addCommand(new ViewTasksByDateRangeCommand(controller, scanner));
        menu.addCommand(new ViewTasksByStatusCommand(controller, scanner));
        menu.addCommand(new ViewTasksByDueDateCommand(controller, scanner));
        menu.addCommand(new SearchTasksByKeywordCommand(controller, scanner));
        menu.addCommand(new ViewTaskHistoryCommand(controller, scanner));
        
        // Tag commands
        menu.addCommand(new AddTagToTaskCommand(controller, scanner));
        menu.addCommand(new ViewTasksByTagCommand(controller, scanner));
        
        // Subtask commands
        menu.addCommand(new AddSubtaskCommand(controller, scanner));
        menu.addCommand(new UpdateSubtaskStatusCommand(controller, scanner));
        
        // Project commands
        menu.addCommand(new CreateProjectCommand(controller, scanner));
        menu.addCommand(new AddTaskToProjectCommand(controller, scanner));
        menu.addCommand(new RemoveTaskFromProjectCommand(controller, scanner));
        menu.addCommand(new ViewTasksByProjectCommand(controller, scanner));
        
        // Import/Export commands
        menu.addCommand(new ExportTasksCommand(controller, scanner));
        menu.addCommand(new ImportTasksCommand(controller, scanner));
        menu.addCommand(new ExportTaskToCalendarCommand(controller, scanner));
        
        // Collaborator commands
        menu.addCommand(new CreateCollaboratorCommand(controller, scanner));
        menu.addCommand(new AddCollaboratorToTaskCommand(controller, scanner));
        menu.addCommand(new AddCollaboratorToProjectCommand(controller, scanner));
        
        // Recurring task commands
        menu.addCommand(new CreateRecurringTaskCommand(controller, scanner));
        
        // Help command
        menu.addCommand(new HelpCommand(menu.getCommands()));

        menu.run();
    }
}
