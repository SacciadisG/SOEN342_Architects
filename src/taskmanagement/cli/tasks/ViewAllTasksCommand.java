package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import taskmanagement.domain.Tag;
import taskmanagement.domain.Project;
import taskmanagement.enums.PriorityEnum;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class ViewAllTasksCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public ViewAllTasksCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.println("""
                How would you like to view tasks?
                1. By Task ID (default)
                2. By Due Date
                3. By Priority
                4. By Status
                5. By Project
                6. By Tag
                """);
            System.out.print("> ");
            String choice = scanner.nextLine().trim();
            
            List<Task> allTasks = controller.getAllTasks();
            
            if (allTasks == null || allTasks.isEmpty()) {
                System.out.println("No tasks found.");
                return;
            }
            
            switch(choice) {
                case "1":
                case "":
                    displayByTaskId(allTasks);
                    break;
                case "2":
                    displayByDueDate(allTasks);
                    break;
                case "3":
                    displayByPriority(allTasks);
                    break;
                case "4":
                    displayByStatus(allTasks);
                    break;
                case "5":
                    displayByProject(allTasks);
                    break;
                case "6":
                    displayByTag(allTasks);
                    break;
                default:
                    System.out.println("Error: Invalid option. Please choose 1-6.");
            }
        } catch (Exception e) {
            System.out.println("Error: Failed to retrieve tasks. " + e.getMessage());
        }
    }

    private void displayByTaskId(List<Task> tasks) {
        System.out.println("\n=== All Tasks (sorted by Task ID) ===");
        tasks.sort((t1, t2) -> t1.getTaskId().compareTo(t2.getTaskId()));
        displayTasks(tasks);
    }

    private void displayByDueDate(List<Task> tasks) {
        System.out.println("\n=== All Tasks (sorted by Due Date) ===");
        tasks.sort((t1, t2) -> {
            if (t1.getDueDate() == null && t2.getDueDate() == null) return 0;
            if (t1.getDueDate() == null) return 1;
            if (t2.getDueDate() == null) return -1;
            return t1.getDueDate().compareTo(t2.getDueDate());
        });
        displayTasks(tasks);
    }

    private void displayByPriority(List<Task> tasks) {
        System.out.println("\n=== All Tasks (sorted by Priority) ===");
        tasks.sort((t1, t2) -> t2.getPriority().compareTo(t1.getPriority()));
        displayTasks(tasks);
    }

    private void displayByStatus(List<Task> tasks) {
        System.out.println("\n=== All Tasks (sorted by Status) ===");
        tasks.sort((t1, t2) -> t1.getStatus().compareTo(t2.getStatus()));
        displayTasks(tasks);
    }

    private void displayByProject(List<Task> tasks) {
        System.out.println("\n=== All Tasks (sorted by Project) ===");
        tasks.sort((t1, t2) -> {
            String p1 = t1.getProject() != null ? t1.getProject().getName() : "Unassigned";
            String p2 = t2.getProject() != null ? t2.getProject().getName() : "Unassigned";
            return p1.compareTo(p2);
        });
        displayTasks(tasks);
    }

    private void displayByTag(List<Task> tasks) {
        System.out.println("\n=== All Tasks (sorted by Tag) ===");
        tasks.sort((t1, t2) -> {
            String tags1 = t1.getTags().isEmpty() ? "Untagged" : t1.getTags().get(0).getName();
            String tags2 = t2.getTags().isEmpty() ? "Untagged" : t2.getTags().get(0).getName();
            return tags1.compareTo(tags2);
        });
        displayTasks(tasks);
    }

    private void displayTasks(List<Task> tasks) {
        for (Task task : tasks) {
            System.out.println("\nID: " + task.getTaskId());
            System.out.println("Title: " + task.getTitle());
            if (task.getDescription() != null && !task.getDescription().isEmpty()) {
                System.out.println("Description: " + task.getDescription());
            }
            System.out.println("Status: " + task.getStatus());
            System.out.println("Priority: " + task.getPriority());
            if (task.getDueDate() != null) {
                System.out.println("Due Date: " + task.getDueDate());
            }
            if (task.getProject() != null) {
                System.out.println("Project: " + task.getProject().getName());
            }
            if (!task.getTags().isEmpty()) {
                System.out.println("Tags: " + task.getTags());
            }
        }
        System.out.println("\nTotal tasks: " + tasks.size());
    }

    @Override
    public String getName() {
        return "view-all-tasks";
    }

    @Override
    public String getDescription() {
        return "View all tasks with sorting options";
    }
}
