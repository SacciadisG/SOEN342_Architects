package taskmanagement.cli.tags;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Tag;
import taskmanagement.domain.Task;

import java.util.List;
import java.util.Scanner;

public class ViewTasksByTagCommand implements Command {
    private SystemController controller;
    private Scanner scanner;

    public ViewTasksByTagCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("View Tasks by Tag");
        System.out.print("Enter tag name: ");
        String tagName = scanner.nextLine().trim();
        
        Tag tag = controller.getTagCatalog().findTag(tagName);
        
        if (tag == null) {
            System.out.println("Tag not found: " + tagName);
            return;
        }

        List<Task> tasks = controller.filterTasksByTag(tag);
        
        if (tasks.isEmpty()) {
            System.out.println("No tasks found with tag: " + tagName);
        } else {
            System.out.println("\n=== Tasks with Tag: " + tagName + " ===");
            for (Task task : tasks) {
                System.out.println("ID: " + task.getTaskId() + " | Title: " + task.getTitle() + 
                                 " | Status: " + task.getStatus() + " | Due: " + task.getDueDate());
            }
        }
    }

    @Override
    public String getName() {
        return "view-by-tag";
    }

    @Override
    public String getDescription() {
        return "View all tasks filtered by a specific tag";
    }
}
