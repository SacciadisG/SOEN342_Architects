package taskmanagement.cli;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CLIMenu {
    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final Scanner scanner;

    public CLIMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void addCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void run() {
        while (true) {
            System.out.println("\n--- Personal Task Management System ---");
            System.out.println("Type 'help' for a list of available commands.");
            System.out.print("Enter command: ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            Command cmd = commands.get(input);
            if (cmd != null) {
                cmd.execute();
            } else {
                System.out.println("Unknown command. Type 'help' for available commands.");
            }
        }
    }
}
