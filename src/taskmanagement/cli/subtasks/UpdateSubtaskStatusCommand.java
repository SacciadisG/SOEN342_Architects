package taskmanagement.cli.subtasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import taskmanagement.domain.Subtask;
import taskmanagement.enums.StatusEnum;
import java.util.Scanner;

public class UpdateSubtaskStatusCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public UpdateSubtaskStatusCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Enter task ID: ");
            Long taskId;
            try {
                taskId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Task ID must be a valid number.");
                return;
            }
            
            Task task = controller.getTaskCatalog().findTask(taskId);
            if (task == null) {
                System.out.println("Error: Task with ID " + taskId + " does not exist.");
                return;
            }
            
            if (task.getSubtasks().isEmpty()) {
                System.out.println("Error: Task has no subtasks.");
                return;
            }
            
            System.out.println("Subtasks:");
            for (int i = 0; i < task.getSubtasks().size(); i++) {
                Subtask st = task.getSubtasks().get(i);
                System.out.println("  " + i + ". " + st.getTitle() + " (" + st.getStatus() + ")");
            }
            
            System.out.print("Enter subtask ID: ");
            Long subtaskId;
            try {
                subtaskId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Subtask ID must be a valid number.");
                return;
            }
            
            Subtask subtask = null;
            for (Subtask st : task.getSubtasks()) {
                if (st.getSubtaskId().equals(subtaskId)) {
                    subtask = st;
                    break;
                }
            }
            
            if (subtask == null) {
                System.out.println("Error: Subtask with ID " + subtaskId + " not found.");
                return;
            }
            
            System.out.println("""
                Select new status:
                1. OPEN
                2. COMPLETED
                3. CANCELLED
                """);
            System.out.print("> ");
            String choice = scanner.nextLine().trim();
            
            StatusEnum newStatus;
            switch(choice) {
                case "1":
                    newStatus = StatusEnum.OPEN;
                    break;
                case "2":
                    newStatus = StatusEnum.COMPLETED;
                    break;
                case "3":
                    newStatus = StatusEnum.CANCELLED;
                    break;
                default:
                    System.out.println("Error: Invalid status selection.");
                    return;
            }
            
            controller.updateSubtaskStatus(taskId, subtaskId, newStatus);
            System.out.println("Subtask status updated to " + newStatus);
            controller.logTaskAction(taskId, "Subtask status changed to " + newStatus);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "update-subtask-status";
    }

    @Override
    public String getDescription() {
        return "Update a subtask's status";
    }
}
