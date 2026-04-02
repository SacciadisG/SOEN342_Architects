package taskmanagement.gateway;

import taskmanagement.domain.Task;
import taskmanagement.domain.Subtask;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Gateway class that interfaces between the domain layer (Task objects)
 * and iCalendar format (.ics). Uses Gateway pattern to decouple domain
 * from external calendar library dependencies.
 */
public class ICalendarGateway {
    private static final String ICAL_VERSION = "2.0";
    private static final String ICAL_PRODUCT_ID = "-//Task Management System//EN";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Converts a single Task to iCalendar (ICS) format string
     * @param task The task to convert
     * @return ICS formatted string for the task, or empty string if task has no due date
     */
    public String taskToICalendarEvent(Task task) {
        if (task.getDueDate() == null) {
            return "";
        }

        StringBuilder event = new StringBuilder();
        event.append("BEGIN:VEVENT\n");
        event.append("UID:").append(UUID.randomUUID()).append("@tasksystem.local\n");
        event.append("DTSTAMP:").append(getCurrentTimestamp()).append("\n");
        event.append("DTSTART:").append(formatDate(task.getDueDate())).append("\n");
        event.append("SUMMARY:").append(escapeICalText(task.getTitle())).append("\n");

        // Build description with task details and subtask summary
        StringBuilder description = new StringBuilder();
        description.append(escapeICalText(task.getDescription())).append("\n");
        description.append("Status: ").append(task.getStatus()).append("\n");
        description.append("Priority: ").append(task.getPriority()).append("\n");
        
        if (task.getProject() != null) {
            description.append("Project: ").append(escapeICalText(task.getProject().getName())).append("\n");
        }

        // Add subtask summary to description
        if (task.getSubtasks() != null && !task.getSubtasks().isEmpty()) {
            description.append("\nSubtasks:\n");
            for (Subtask subtask : task.getSubtasks()) {
                description.append("  - ").append(escapeICalText(subtask.getTitle()))
                        .append(" [").append(subtask.getStatus()).append("]\n");
            }
        }

        event.append("DESCRIPTION:").append(description.toString().trim()).append("\n");
        event.append("END:VEVENT\n");

        return event.toString();
    }

    /**
     * Converts a list of Tasks to a complete iCalendar file format
     * @param tasks List of tasks to convert
     * @return Complete ICS file content string
     */
    public String tasksToICalendarFile(List<Task> tasks) {
        StringBuilder icsFile = new StringBuilder();
        icsFile.append("BEGIN:VCALENDAR\n");
        icsFile.append("VERSION:").append(ICAL_VERSION).append("\n");
        icsFile.append("PRODID:").append(ICAL_PRODUCT_ID).append("\n");
        icsFile.append("CALSCALE:GREGORIAN\n");
        icsFile.append("METHOD:PUBLISH\n");

        // Add all task events (filtering out tasks without due dates)
        for (Task task : tasks) {
            if (task.getDueDate() != null) {
                icsFile.append(taskToICalendarEvent(task));
            }
        }

        icsFile.append("END:VCALENDAR\n");
        return icsFile.toString();
    }

    /**
     * Formats a LocalDate to iCalendar date format (YYYYMMDD)
     */
    private String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * Gets current timestamp in iCalendar format
     */
    private String getCurrentTimestamp() {
        LocalDate now = LocalDate.now();
        return now.format(DATE_FORMATTER) + "T000000Z";
    }

    /**
     * Escapes special characters for iCalendar text fields
     * Handles: newlines, commas, semicolons, backslashes, quotes
     */
    private String escapeICalText(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\")
                   .replace("\n", "\\n")
                   .replace(",", "\\,")
                   .replace(";", "\\;");
    }
}
