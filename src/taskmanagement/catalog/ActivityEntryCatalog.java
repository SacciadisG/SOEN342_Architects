package taskmanagement.catalog;

import taskmanagement.domain.ActivityEntry;

import java.util.List;
import java.util.ArrayList;

public class ActivityEntryCatalog {
    private List<ActivityEntry> entries = new ArrayList<>();
    
    public void addActivityEntry(Long taskId, String description) {
        ActivityEntry entry = new ActivityEntry(taskId, description);
        entries.add(entry);
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
}
