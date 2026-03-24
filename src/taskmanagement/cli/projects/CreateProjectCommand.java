package taskmanagement.cli.projects;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Project;
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
        try {
            System.out.print("Enter project name: ");
            String projectName = scanner.nextLine().trim();
            
            if (projectName.isEmpty()) {
                System.out.println("Error: Project name cannot be empty.");
                return;
            }
            
            System.out.print("Enter project description (or leave blank for none): ");
            String projectDescription = scanner.nextLine().trim();
            
            Project project;
            if (projectDescription.isEmpty()) {
                project = controller.createProject(projectName);
            } else {
                project = controller.createProject(projectName, projectDescription);
            }
            
            System.out.println("Project created successfully with ID: " + project.getProjectId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "create-project";
    }

    @Override
    public String getDescription() {
        return "Create a new project";
    }
}
