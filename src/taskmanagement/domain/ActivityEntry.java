package taskmanagement.domain;

import java.time.LocalDateTime;

public class ActivityEntry {
    private Long activityId;
    private LocalDateTime timestamp;
    private String description;

    public ActivityEntry(Long activityId, LocalDateTime timestamp, String description) {
        this.activityId = activityId;
        this.timestamp = timestamp;
        this.description = description;
    }


    // Getters and Setters
    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
