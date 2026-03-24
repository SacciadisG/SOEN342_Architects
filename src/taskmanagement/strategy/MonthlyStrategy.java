package taskmanagement.strategy;

import java.time.LocalDate;
import java.time.YearMonth;

public class MonthlyStrategy extends RecurrenceStrategy {
    private int dayOfMonth;

    public MonthlyStrategy(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public LocalDate getNextOccurrenceDate(LocalDate lastGeneratedDate, LocalDate endDate) {
        if (lastGeneratedDate == null || dayOfMonth < 1 || dayOfMonth > 31) {
            return null;
        }
        
        // Move to the next month from lastGeneratedDate
        YearMonth nextMonth = YearMonth.from(lastGeneratedDate).plusMonths(1);
        
        // Attempt to create date on the specified day of month
        LocalDate nextDate;
        try {
            nextDate = nextMonth.atDay(dayOfMonth);
        } catch (java.time.DateTimeException e) {
            // If day doesn't exist (e.g., Feb 30), use last day of month
            nextDate = nextMonth.atEndOfMonth();
        }
        
        // Check if it's within the end date
        if (!nextDate.isAfter(endDate)) {
            return nextDate;
        }
        
        return null;
    }

    // Getters and Setters
    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }
}
