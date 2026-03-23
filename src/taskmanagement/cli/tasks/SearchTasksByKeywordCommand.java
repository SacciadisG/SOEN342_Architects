package taskmanagement.cli.tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;

import java.util.List;
import java.util.Scanner;

public class SearchTasksByKeywordCommand implements Command {
    private SystemController controller;
    private Scanner scanner;

    public SearchTasksByKeywordCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Search Tasks by Keyword");
        System.out.print("Enter keyword to search for: ");
        String keyword = scanner.nextLine().trim();
        
        if (keyword.isEmpty()) {
            System.out.println("Keyword cannot be empty.");
            return;
        }

        List<Task> tasks = controller.searchTasksByKeyword(keyword);
        
        if (tasks.isEmpty()) {
            System.out.println("No tasks found matching keyword: " + keyword);
        } else {
            System.out.println("\n=== Tasks matching keyword: \"" + keyword + "\" ===");
            for (Task task : tasks) {
                System.out.println("ID: " + task.getTaskId() + " | Title: " + task.getTitle() + 
                                 " | Status: " + task.getStatus() + " | Due: " + task.getDueDate());
            }
        }
    }

    @Override
    public String getName() {
        return "search-keyword";
    }

    @Override
    public String getDescription() {
        return "Search tasks by keyword in title or description";
    }
}
