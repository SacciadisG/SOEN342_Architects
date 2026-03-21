package taskmanagement.strategy;

import taskmanagement.domain.Task;

public class MonthlyStrategy extends RecurrenceStrategy {
    private int dayOfMonth;

    public MonthlyStrategy(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public Task generateReoccuringTask() {
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
