package taskmanagement.domain;

public class IntermediateCollaborator extends Collaborator {
    private static final int MAX_OPEN_TASKS = 5;

    public IntermediateCollaborator(Long collaboratorId, String name) {
        super(collaboratorId, name);
        this.maxOpenTasks = MAX_OPEN_TASKS;
    }
}
