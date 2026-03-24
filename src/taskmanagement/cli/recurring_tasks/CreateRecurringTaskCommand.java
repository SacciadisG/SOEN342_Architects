package taskmanagement.cli.recurring_tasks;

import taskmanagement.cli.Command;
import taskmanagement.controller.SystemController;
import taskmanagement.strategy.*;
import taskmanagement.domain.Task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateRecurringTaskCommand implements Command {
    private SystemController controller;
    private Scanner scanner;

    public CreateRecurringTaskCommand(SystemController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.println("Enter task title:");
            String title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Error: Task title cannot be empty");
                return;
            }

            System.out.println("Enter task description:");
            String description = scanner.nextLine().trim();

            System.out.println("Enter start date (yyyy-MM-dd):");
            LocalDate startDate = LocalDate.parse(scanner.nextLine().trim());

            System.out.println("Enter end date (yyyy-MM-dd):");
            LocalDate endDate = LocalDate.parse(scanner.nextLine().trim());

            if (startDate.isAfter(endDate)) {
                System.out.println("Error: Start date must be before or equal to end date");
                return;
            }

            System.out.println("Select recurrence pattern:");
            System.out.println("1. Daily");
            System.out.println("2. Weekly");
            System.out.println("3. Monthly");
            System.out.print("Enter choice (1-3): ");
            String choice = scanner.nextLine().trim();

            RecurrenceStrategy strategy;

            switch (choice) {
                case "1":
                    System.out.println("Enter interval in days (default 1):");
                    String intervalStr = scanner.nextLine().trim();
                    int interval = intervalStr.isEmpty() ? 1 : Integer.parseInt(intervalStr);
                    if (interval <= 0) {
                        System.out.println("Error: Interval must be positive");
                        return;
                    }
                    strategy = new DailyStrategy(interval);
                    break;

                case "2":
                    System.out.println("Enter days of week (comma-separated, e.g., MONDAY,WEDNESDAY,FRIDAY):");
                    String daysStr = scanner.nextLine().trim().toUpperCase();
                    List<DayOfWeek> selectedDays = new ArrayList<>();
                    for (String day : daysStr.split(",")) {
                        try {
                            selectedDays.add(DayOfWeek.valueOf(day.trim()));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: Invalid day of week: " + day);
                            return;
                        }
                    }
                    if (selectedDays.isEmpty()) {
                        System.out.println("Error: At least one day must be selected");
                        return;
                    }
                    strategy = new WeeklyStrategy(selectedDays);
                    break;

                case "3":
                    System.out.println("Enter day of month (1-31):");
                    int dayOfMonth = Integer.parseInt(scanner.nextLine().trim());
                    if (dayOfMonth < 1 || dayOfMonth > 31) {
                        System.out.println("Error: Day of month must be between 1 and 31");
                        return;
                    }
                    strategy = new MonthlyStrategy(dayOfMonth);
                    break;

                default:
                    System.out.println("Error: Invalid choice. Please enter 1, 2, or 3");
                    return;
            }

            Task recurringTask = controller.createRecurringTask(title, description, startDate, endDate, strategy);
            System.out.println("Recurring task created successfully!");
            System.out.println("Task ID: " + recurringTask.getTaskId());
            System.out.println("Title: " + recurringTask.getTitle());
            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);

        } catch (Exception e) {
            System.out.println("Error creating recurring task: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "create-recurring-task";
    }

    @Override
    public String getDescription() {
        return "Create a recurring task with daily, weekly, or monthly pattern";
    }
}
