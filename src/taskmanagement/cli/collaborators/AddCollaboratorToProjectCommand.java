package taskmanagement.cli.collaborators;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;

import java.util.Scanner;

public class AddCollaboratorToProjectCommand implements Command {
    private SystemController controller;
    private Scanner scanner;

    public AddCollaboratorToProjectCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.println("Enter project ID:");
            Long projectId = Long.parseLong(scanner.nextLine().trim());

            System.out.println("Enter collaborator ID:");
            Long collaboratorId = Long.parseLong(scanner.nextLine().trim());

            controller.addCollaboratorToProject(projectId, collaboratorId);
            System.out.println("Collaborator added to project successfully!");
            System.out.println("Note: Assign this collaborator to specific tasks within the project using add-collaborator-to-task");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID format. Please enter valid numeric IDs");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error adding collaborator to project: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "add-collaborator-to-project";
    }

    @Override
    public String getDescription() {
        return "Add a collaborator to a project";
    }
}
