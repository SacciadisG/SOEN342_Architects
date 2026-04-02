package taskmanagement.persistence;

import taskmanagement.domain.Project;
import taskmanagement.domain.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for Project persistence to database
 */
public class ProjectRepository {
    private DatabaseManager dbManager;
    private TaskRepository taskRepository;

    public ProjectRepository(DatabaseManager dbManager, TaskRepository taskRepository) {
        this.dbManager = dbManager;
        this.taskRepository = taskRepository;
    }

    /**
     * Save a project to the database
     */
    public void saveProject(Project project) throws SQLException {
        String sql = "INSERT OR REPLACE INTO projects (project_id, name, description) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, project.getProjectId());
            pstmt.setString(2, project.getName());
            pstmt.setString(3, project.getDescription());
            pstmt.executeUpdate();
        }
    }

    /**
     * Load a project from the database by ID
     */
    public Project loadProject(Long projectId) throws SQLException {
        String sql = "SELECT * FROM projects WHERE project_id = ?";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, projectId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProject(rs);
                }
            }
        }
        return null;
    }

    /**
     * Load all projects from the database
     */
    public List<Project> loadAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects";
        
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                projects.add(mapResultSetToProject(rs));
            }
        }
        return projects;
    }

    /**
     * Delete a project from the database
     */
    public void deleteProject(Long projectId) throws SQLException {
        // Delete all task associations with this project
        String updateSQL = "UPDATE tasks SET project_id = NULL WHERE project_id = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(updateSQL)) {
            pstmt.setLong(1, projectId);
            pstmt.executeUpdate();
        }

        // Delete the project itself
        String deleteSQL = "DELETE FROM projects WHERE project_id = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(deleteSQL)) {
            pstmt.setLong(1, projectId);
            pstmt.executeUpdate();
        }
    }

    /**
     * Map ResultSet row to Project object
     */
    private Project mapResultSetToProject(ResultSet rs) throws SQLException {
        Long projectId = rs.getLong("project_id");
        String name = rs.getString("name");
        String description = rs.getString("description");

        Project project = new Project(name, description);
        project.setProjectId(projectId);

        // Load tasks for this project
        String sql = "SELECT task_id FROM tasks WHERE project_id = ?";
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, projectId);
            try (ResultSet taskRs = pstmt.executeQuery()) {
                while (taskRs.next()) {
                    Task task = taskRepository.loadTask(taskRs.getLong("task_id"));
                    if (task != null) {
                        project.addTaskToProject(task);
                    }
                }
            }
        }

        return project;
    }
}
