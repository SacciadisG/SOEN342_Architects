package taskmanagement.cli.projects;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Project;
import java.util.Scanner;

public class AddTaskToProjectCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public AddTaskToProjectCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter task ID:");
        Long taskId = Long.parseLong(scanner.nextLine());
        
        System.out.println("Enter project ID:");
        Long projectId = Long.parseLong(scanner.nextLine());
        
        controller.addTaskToProject(taskId, projectId);
        Project project = controller.getProjectCatalog().findProject(projectId);
        String projectName = (project != null) ? project.getName() : "ID:" + projectId;
        System.out.println("Task added to project successfully.");
        controller.logTaskAction(taskId, "Task added to project: " + projectName);
    }

    @Override
    public String getName() {
        return "addtasktoproject";
    }

    @Override
    public String getDescription() {
        return "Add a task to a project";
    }
}
