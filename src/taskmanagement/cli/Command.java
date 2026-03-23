package taskmanagement.cli;

public interface Command {
    void execute();
    String getName();
    String getDescription();
}
