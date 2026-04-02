package taskmanagement.catalog;

import taskmanagement.domain.ActivityEntry;
import taskmanagement.persistence.DatabaseManager;
import taskmanagement.persistence.ActivityRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ActivityEntryCatalog {
    private List<ActivityEntry> entries = new ArrayList<>();
    private DatabaseManager dbManager;
    private ActivityRepository activityRepository;

    // Constructor without database (for backward compatibility)
    public ActivityEntryCatalog() {
        this.dbManager = null;
        this.activityRepository = null;
    }

    // Constructor with database
    public ActivityEntryCatalog(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        this.activityRepository = new ActivityRepository(dbManager);
    }
    
    public void addActivityEntry(Long taskId, String description) {
        ActivityEntry entry = new ActivityEntry(taskId, description);
        entries.add(entry);
        if (activityRepository != null) {
            try {
                activityRepository.saveActivityEntry(entry);
            } catch (SQLException e) {
                System.err.println("Error saving activity entry to database: " + e.getMessage());
            }
        }
    }

    public ActivityEntry findActivityEntry(Long activityId) {
        for (ActivityEntry entry : entries) {
            if (entry.getActivityId().equals(activityId)) {
                return entry;
            }
        }
        return null;
    }

    public List<ActivityEntry> getHistoryForTask(Long taskId) {
        List<ActivityEntry> taskHistory = new ArrayList<>();
        for (ActivityEntry entry : entries) {
            if (entry.getTaskId().equals(taskId)) {
                taskHistory.add(entry);
            }
        }
        return taskHistory;
    }

    public List<ActivityEntry> getAllEntries() {
        return new ArrayList<>(entries);
    }

    /**
     * Load all activities from database
     */
    public void loadActivitiesFromDatabase() throws SQLException {
        if (activityRepository != null) {
            entries = activityRepository.loadAllActivityEntries();
        }
    }

    /**
     * Save all activities to database
     */
    public void saveAllActivitiesToDatabase() throws SQLException {
        if (activityRepository != null) {
            for (ActivityEntry entry : entries) {
                activityRepository.saveActivityEntry(entry);
            }
        }
    }
}
