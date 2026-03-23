package taskmanagement.cli.projects;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import java.util.Scanner;

public class CreateProjectCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public CreateProjectCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter project name:");
        String projectName = scanner.nextLine();
        
        System.out.println("Enter project description (or leave blank for none):");
        String projectDescription = scanner.nextLine();
        
        if (projectDescription.isEmpty()) {
            controller.createProject(projectName);
        } else {
            controller.createProject(projectName, projectDescription);
        }
        
        System.out.println("Project created successfully.");
    }

    @Override
    public String getName() {
        return "createproject";
    }

    @Override
    public String getDescription() {
        return "Create a new project";
    }
}
