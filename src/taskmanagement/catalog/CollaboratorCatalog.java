package taskmanagement.catalog;

import taskmanagement.domain.Collaborator;
import taskmanagement.domain.Task;
import taskmanagement.domain.Subtask;
import taskmanagement.enums.StatusEnum;
import java.util.List;
import java.util.ArrayList;

public class CollaboratorCatalog {
    private List<Collaborator> collaborators;
    private TaskCatalog taskCatalog;
    private Long nextCollaboratorId;

    public CollaboratorCatalog(TaskCatalog taskCatalog) {
        this.collaborators = new ArrayList<>();
        this.taskCatalog = taskCatalog;
        this.nextCollaboratorId = 1L;
    }

    public void addCollaborator(Collaborator collaborator) {
        if (!collaborators.contains(collaborator)) {
            collaborators.add(collaborator);
            // Update nextCollaboratorId if this collaborator has a higher ID
            if (collaborator.getCollaboratorId() >= nextCollaboratorId) {
                nextCollaboratorId = collaborator.getCollaboratorId() + 1;
            }
        }
    }

    public Long getNextCollaboratorId() {
        return nextCollaboratorId++;
    }

    public Collaborator findCollaborator(Long id) {
        return collaborators.stream()
                .filter(c -> c.getCollaboratorId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Collaborator> getAllCollaborators() {
        return new ArrayList<>(collaborators);
    }

    /**
     * Gets the count of OPEN subtasks assigned to a collaborator
     */
    public int getOpenTaskCount(Collaborator collaborator) {
        int count = 0;
        List<Task> allTasks = taskCatalog.displayTasksNoFilter();
        
        for (Task task : allTasks) {
            for (Subtask subtask : task.getSubtasks()) {
                if (subtask.hasCollaborator() && 
                    subtask.getCollaborator().getCollaboratorId().equals(collaborator.getCollaboratorId()) &&
                    subtask.getStatus() == StatusEnum.OPEN) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if a collaborator can be assigned a new task
     */
    public boolean canAssignTask(Collaborator collaborator) {
        int openCount = getOpenTaskCount(collaborator);
        return openCount < collaborator.getMaxOpenTasks();
    }

    /**
     * Gets open subtasks assigned to a collaborator
     */
    public List<Subtask> getOpenSubtasksForCollaborator(Collaborator collaborator) {
        List<Subtask> result = new ArrayList<>();
        List<Task> allTasks = taskCatalog.displayTasksNoFilter();
        
        for (Task task : allTasks) {
            for (Subtask subtask : task.getSubtasks()) {
                if (subtask.hasCollaborator() && 
                    subtask.getCollaborator().getCollaboratorId().equals(collaborator.getCollaboratorId()) &&
                    subtask.getStatus() == StatusEnum.OPEN) {
                    result.add(subtask);
                }
            }
        }
        return result;
    }
}
