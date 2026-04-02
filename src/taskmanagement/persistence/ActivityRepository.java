package taskmanagement.persistence;

import taskmanagement.domain.ActivityEntry;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for ActivityEntry persistence to database
 */
public class ActivityRepository {
    private DatabaseManager dbManager;

    public ActivityRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Save an activity entry to the database
     */
    public void saveActivityEntry(ActivityEntry entry) throws SQLException {
        String sql = "INSERT INTO activity_entries (activity_id, task_id, timestamp, description) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, entry.getActivityId());
            pstmt.setLong(2, entry.getTaskId());
            pstmt.setString(3, entry.getTimestamp().toString());
            pstmt.setString(4, entry.getDescription());
            pstmt.executeUpdate();
        }
    }

    /**
     * Load all activity entries for a specific task
     */
    public List<ActivityEntry> loadActivityEntriesForTask(Long taskId) throws SQLException {
        List<ActivityEntry> entries = new ArrayList<>();
        String sql = "SELECT * FROM activity_entries WHERE task_id = ? ORDER BY timestamp ASC";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, taskId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ActivityEntry entry = mapResultSetToActivityEntry(rs);
                    entries.add(entry);
                }
            }
        }
        return entries;
    }

    /**
     * Load all activity entries from the database
     */
    public List<ActivityEntry> loadAllActivityEntries() throws SQLException {
        List<ActivityEntry> entries = new ArrayList<>();
        String sql = "SELECT * FROM activity_entries ORDER BY timestamp DESC";
        
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                entries.add(mapResultSetToActivityEntry(rs));
            }
        }
        return entries;
    }

    /**
     * Delete all activity entries for a specific task
     */
    public void deleteActivityEntriesForTask(Long taskId) throws SQLException {
        String sql = "DELETE FROM activity_entries WHERE task_id = ?";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, taskId);
            pstmt.executeUpdate();
        }
    }

    /**
     * Map ResultSet row to ActivityEntry object
     */
    private ActivityEntry mapResultSetToActivityEntry(ResultSet rs) throws SQLException {
        Long activityId = rs.getLong("activity_id");
        Long taskId = rs.getLong("task_id");
        LocalDateTime timestamp = LocalDateTime.parse(rs.getString("timestamp"));
        String description = rs.getString("description");

        ActivityEntry entry = new ActivityEntry(taskId, description);
        entry.setActivityId(activityId);
        entry.setTimestamp(timestamp);

        return entry;
    }
}
