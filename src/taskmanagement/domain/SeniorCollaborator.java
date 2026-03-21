package taskmanagement.domain;

public class SeniorCollaborator extends Collaborator {
    private static final int MAX_OPEN_TASKS = 2;

    public SeniorCollaborator(Long collaboratorId, String name) {
        super(collaboratorId, name);
        this.maxOpenTasks = MAX_OPEN_TASKS;
    }
}
