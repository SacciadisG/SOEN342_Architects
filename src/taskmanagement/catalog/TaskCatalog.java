package taskmanagement.catalog;

import taskmanagement.domain.Task;
import taskmanagement.enums.StatusEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskCatalog {

    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1L;

    public void addTask(Task task) {
        if (task.getTaskId() == null) {
            task.setTaskId(nextId++);
        }
        tasks.add(task);
    }

    public void updateTask(Long taskId, Object details) {
    }

    public Task findTask(Long taskId) {
        return tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst()
                .orElse(null);
    }

    public List<Task> findTasksByCriteria(Object criteria) {
        String query = (criteria != null) ? criteria.toString().trim() : "";

        if (query.isBlank()) {
            // Default: all OPEN tasks sorted by due date ascending
            return tasks.stream()
                    .filter(t -> t.getStatus() == StatusEnum.OPEN)
                    .sorted((a, b) -> {
                        if (a.getDueDate() == null && b.getDueDate() == null) return 0;
                        if (a.getDueDate() == null) return 1;
                        if (b.getDueDate() == null) return -1;
                        return a.getDueDate().compareTo(b.getDueDate());
                    })
                    .collect(Collectors.toList());
        }

        String lower = query.toLowerCase();
        return tasks.stream()
                .filter(t -> (t.getTitle() != null && t.getTitle().toLowerCase().contains(lower))
                          || (t.getDescription() != null && t.getDescription().toLowerCase().contains(lower)))
                .collect(Collectors.toList());
    }

    // Extra helpers used by CSVHandler
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public Task findTaskByTitle(String title) {
        return tasks.stream()
                .filter(t -> t.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }
}
