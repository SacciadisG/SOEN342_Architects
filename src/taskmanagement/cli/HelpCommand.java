package taskmanagement.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute() {
        System.out.println("\n=== Available Commands ===\n");
        
        List<String> sortedNames = new ArrayList<>(commands.keySet());
        sortedNames.sort(String::compareTo);
        
        for (String name : sortedNames) {
            Command cmd = commands.get(name);
            System.out.printf("  %-30s %s\n", name, cmd.getDescription());
        }
        
        System.out.println();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Display this help message";
    }
}
