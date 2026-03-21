package taskmanagement.strategy;

import taskmanagement.domain.Task;

import java.time.LocalDate;

public class RecurrencePattern {
    private RecurrenceStrategy strategy;
    private LocalDate startDate;
    private LocalDate endDate;

    public RecurrencePattern(RecurrenceStrategy strategy, LocalDate startDate, LocalDate endDate) {
        this.strategy = strategy;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Task generateReoccuringTask() {
        return null;
    }

    // Getters and Setters
    
    public RecurrenceStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(RecurrenceStrategy strategy) {
        this.strategy = strategy;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
