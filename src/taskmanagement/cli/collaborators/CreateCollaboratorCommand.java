package taskmanagement.cli.collaborators;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Collaborator;

import java.util.Scanner;

public class CreateCollaboratorCommand implements Command {
    private SystemController controller;
    private Scanner scanner;

    public CreateCollaboratorCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.println("Enter collaborator name:");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Error: Collaborator name cannot be empty");
                return;
            }

            System.out.println("Enter collaborator type (senior/intermediate/junior):");
            String type = scanner.nextLine().trim().toLowerCase();
            if (!type.equals("senior") && !type.equals("intermediate") && !type.equals("junior")) {
                System.out.println("Error: Invalid collaborator type. Must be senior, intermediate, or junior");
                return;
            }

            Collaborator newCollaborator = controller.createCollaborator(name, type);
            System.out.println("Collaborator created successfully!");
            System.out.println("ID: " + newCollaborator.getCollaboratorId());
            System.out.println("Name: " + newCollaborator.getName());
            System.out.println("Type: " + type);
            System.out.println("Max Open Tasks: " + newCollaborator.getMaxOpenTasks());
        } catch (Exception e) {
            System.out.println("Error creating collaborator: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "create-collaborator";
    }

    @Override
    public String getDescription() {
        return "Create a new collaborator (senior, intermediate, or junior)";
    }
}
