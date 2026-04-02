package taskmanagement.persistence;

import taskmanagement.domain.Task;
import taskmanagement.domain.Tag;
import taskmanagement.domain.Subtask;
import taskmanagement.enums.StatusEnum;
import taskmanagement.enums.PriorityEnum;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for Task persistence to database
 */
public class TaskRepository {
    private DatabaseManager dbManager;

    public TaskRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Save a task to the database
     */
    public void saveTask(Task task) throws SQLException {
        String sql = "INSERT OR REPLACE INTO tasks (task_id, title, description, creation_date, due_date, priority, status, project_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, task.getTaskId());
            pstmt.setString(2, task.getTitle());
            pstmt.setString(3, task.getDescription());
            pstmt.setString(4, task.getCreationDate().toString());
            pstmt.setString(5, task.getDueDate() != null ? task.getDueDate().toString() : null);
            pstmt.setString(6, task.getPriority().toString());
            pstmt.setString(7, task.getStatus().toString());
            pstmt.setLong(8, task.getProject() != null ? task.getProject().getProjectId() : null);
            pstmt.executeUpdate();
        }

        // Save tags relationship
        saveTags(task);

        // Save subtasks
        saveSubtasks(task);
    }

    /**
     * Load a task from the database by ID
     */
    public Task loadTask(Long taskId) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE task_id = ?";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, taskId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTask(rs);
                }
            }
        }
        return null;
    }

    /**
     * Load all tasks from the database
     */
    public List<Task> loadAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tasks.add(mapResultSetToTask(rs));
            }
        }
        return tasks;
    }

    /**
     * Delete a task from the database
     */
    public void deleteTask(Long taskId) throws SQLException {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, taskId);
            pstmt.executeUpdate();
        }
    }

    /**
     * Save task tags relationship
     */
    private void saveTags(Task task) throws SQLException {
        // Clear existing tags for this task
        String deleteSQL = "DELETE FROM task_tags WHERE task_id = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(deleteSQL)) {
            pstmt.setLong(1, task.getTaskId());
            pstmt.executeUpdate();
        }

        // Save new tags
        if (task.getTags() != null && !task.getTags().isEmpty()) {
            String insertSQL = "INSERT INTO task_tags (task_id, tag_id) VALUES (?, ?)";
            try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(insertSQL)) {
                for (Tag tag : task.getTags()) {
                    pstmt.setLong(1, task.getTaskId());
                    pstmt.setLong(2, tag.getTagId() != null ? tag.getTagId() : getOrCreateTagId(tag.getName()));
                    pstmt.executeUpdate();
                }
            }
        }
    }

    /**
     * Save task subtasks
     */
    private void saveSubtasks(Task task) throws SQLException {
        if (task.getSubtasks() == null || task.getSubtasks().isEmpty()) {
            return;
        }

        String insertSQL = "INSERT OR REPLACE INTO subtasks (subtask_id, task_id, title, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(insertSQL)) {
            for (Subtask subtask : task.getSubtasks()) {
                pstmt.setLong(1, subtask.getSubtaskId());
                pstmt.setLong(2, task.getTaskId());
                pstmt.setString(3, subtask.getTitle());
                pstmt.setString(4, subtask.getStatus().toString());
                pstmt.executeUpdate();
            }
        }
    }

    /**
     * Map ResultSet row to Task object
     */
    private Task mapResultSetToTask(ResultSet rs) throws SQLException {
        Long taskId = rs.getLong("task_id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        LocalDate dueDate = rs.getString("due_date") != null ? LocalDate.parse(rs.getString("due_date")) : null;
        PriorityEnum priority = PriorityEnum.valueOf(rs.getString("priority"));
        StatusEnum status = StatusEnum.valueOf(rs.getString("status"));

        Task task = new Task(title, description, dueDate);
        task.setTaskId(taskId);
        task.setPriority(priority);
        task.setStatus(status);

        // Load tags
        loadTagsForTask(task);

        // Load subtasks
        loadSubtasksForTask(task);

        return task;
    }

    /**
     * Load tags for a specific task
     */
    private void loadTagsForTask(Task task) throws SQLException {
        String sql = "SELECT t.tag_id, t.name FROM tags t " +
                "JOIN task_tags tt ON t.tag_id = tt.tag_id " +
                "WHERE tt.task_id = ?";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, task.getTaskId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Tag tag = new Tag(rs.getString("name"));
                    tag.setTagId(rs.getLong("tag_id"));
                    task.addTag(tag);
                }
            }
        }
    }

    /**
     * Load subtasks for a specific task
     */
    private void loadSubtasksForTask(Task task) throws SQLException {
        String sql = "SELECT subtask_id, title, status FROM subtasks WHERE task_id = ?";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, task.getTaskId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Subtask subtask = new Subtask(rs.getString("title"));
                    subtask.setSubtaskId(rs.getLong("subtask_id"));
                    subtask.setStatus(StatusEnum.valueOf(rs.getString("status")));
                    task.addSubtask(subtask);
                }
            }
        }
    }

    /**
     * Get or create tag ID by name
     */
    private long getOrCreateTagId(String tagName) throws SQLException {
        String selectSQL = "SELECT tag_id FROM tags WHERE name = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(selectSQL)) {
            pstmt.setString(1, tagName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("tag_id");
                }
            }
        }

        // Create new tag
        String insertSQL = "INSERT INTO tags (name) VALUES (?)";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, tagName);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return 0;
    }
}
