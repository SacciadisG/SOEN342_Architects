package taskmanagement.domain;

import java.time.LocalDateTime;

public class ActivityEntry {
    private static Long ID = 0L;
    private Long activityId;
    private Long taskId;
    private LocalDateTime timestamp;
    private String description;

    public ActivityEntry(Long taskId, String description) {
        this.activityId = ID++;
        this.taskId = taskId;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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
