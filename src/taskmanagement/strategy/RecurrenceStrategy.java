package taskmanagement.strategy;

import taskmanagement.domain.Task;
import java.time.LocalDate;

public abstract class RecurrenceStrategy {
    
    /**
     * Generate the next occurrence due date based on the strategy
     * @param lastGeneratedDate The date of the last generated occurrence
     * @param endDate The end date for the recurrence pattern
     * @return The next due date, or null if beyond endDate
     */
    public abstract LocalDate getNextOccurrenceDate(LocalDate lastGeneratedDate, LocalDate endDate);
    
    /**
     * Generate a task occurrence with the given title, description, and due date
     */
    public Task generateOccurrence(String title, String description, LocalDate dueDate) {
        Task occurrence = new Task(title, description, dueDate);
        return occurrence;
    }
}
