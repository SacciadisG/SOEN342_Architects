package taskmanagement.strategy;

import java.time.LocalDate;

public class DailyStrategy extends RecurrenceStrategy {
    private int interval; // Days between recurrences

    public DailyStrategy(int interval) {
        this.interval = interval;
    }

    public DailyStrategy() {
        this.interval = 1;
    }

    @Override
    public LocalDate getNextOccurrenceDate(LocalDate lastGeneratedDate, LocalDate endDate) {
        if (lastGeneratedDate == null) {
            return null;
        }
        LocalDate nextDate = lastGeneratedDate.plusDays(interval);
        if (nextDate.isAfter(endDate)) {
            return null;
        }
        return nextDate;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
