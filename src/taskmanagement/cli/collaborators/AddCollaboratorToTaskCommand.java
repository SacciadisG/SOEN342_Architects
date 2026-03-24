package taskmanagement.cli.collaborators;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;

import java.util.Scanner;

public class AddCollaboratorToTaskCommand implements Command {
    private SystemController controller;
    private Scanner scanner;

    public AddCollaboratorToTaskCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.println("Enter task ID:");
            Long taskId = Long.parseLong(scanner.nextLine().trim());

            System.out.println("Enter collaborator ID:");
            Long collaboratorId = Long.parseLong(scanner.nextLine().trim());

            controller.addCollaboratorToTask(taskId, collaboratorId);
            System.out.println("Collaborator added to task successfully!");
            System.out.println("A subtask has been created for this assignment.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID format. Please enter valid numeric IDs");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error adding collaborator to task: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "add-collaborator-to-task";
    }

    @Override
    public String getDescription() {
        return "Add a collaborator to a task";
    }
}
