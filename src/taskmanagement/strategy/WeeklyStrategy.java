package taskmanagement.strategy;

import taskmanagement.domain.Task;

import java.time.DayOfWeek;
import java.util.List;

public class WeeklyStrategy extends RecurrenceStrategy {
    private List<DayOfWeek> selectedDays;

    public WeeklyStrategy(List<DayOfWeek> selectedDays) {
        this.selectedDays = selectedDays;
    }

    @Override
    public Task generateReoccuringTask() {
        return null;
    }

    // Getters and Setters
    public List<DayOfWeek> getSelectedDays() {
        return selectedDays;
    }

    public void setSelectedDays(List<DayOfWeek> selectedDays) {
        this.selectedDays = selectedDays;
    }
}
