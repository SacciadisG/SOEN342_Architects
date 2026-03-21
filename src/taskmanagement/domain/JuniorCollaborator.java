package taskmanagement.domain;

public class JuniorCollaborator extends Collaborator {
    private static final int MAX_OPEN_TASKS = 10;

    public JuniorCollaborator(Long collaboratorId, String name) {
        super(collaboratorId, name);
        this.maxOpenTasks = MAX_OPEN_TASKS;
    }
}
