package taskmanagement.catalog;

import taskmanagement.domain.Collaborator;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorCatalog {

    private final List<Collaborator> collaborators = new ArrayList<>();
    private long nextId = 1L;

    public void addCollaborator(Collaborator collaborator) {
        if (collaborator.getCollaboratorId() == null) {
            collaborator.setCollaboratorId(nextId++);
        }
        collaborators.add(collaborator);
    }

    public void updateCollaborator(Long id, Object details) {
    }

    public Collaborator findCollaborator(Long id) {
        return collaborators.stream()
                .filter(c -> c.getCollaboratorId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Used by CSVHandler for auto-create logic (TSK1.03.04)
    public Collaborator findByName(String name) {
        if (name == null || name.isBlank()) return null;
        return collaborators.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Collaborator> getAllCollaborators() {
        return new ArrayList<>(collaborators);
    }
}
