package taskmanagement.cli;

import taskmanagement.controller.SystemController;
import taskmanagement.domain.Task;
import java.util.List;
import java.util.Scanner;

public class SearchTasksCommand implements Command {
    private final SystemController controller;
    private final Scanner scanner;

    public SearchTasksCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("""
                Enter search criteria:
                (options are "title","range","status","day\")
                """);
        String criteria = scanner.nextLine();
        List<Task> results = controller.searchTasks(criteria);
        System.out.println("Search Results:");
        if (results == null || results.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (Task task : results) {
                System.out.println(task.getTitle());
            }
        }
    }

    @Override
    public String getName() {
        return "search";
    }

    @Override
    public String getDescription() {
        return "Search for tasks";
    }
}
