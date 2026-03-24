package taskmanagement.strategy;

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

    public LocalDate getNextOccurrenceDate(LocalDate lastGeneratedDate) {
        if (strategy == null) {
            return null;
        }
        return strategy.getNextOccurrenceDate(lastGeneratedDate, endDate);
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
