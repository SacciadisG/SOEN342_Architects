package taskmanagement.strategy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class WeeklyStrategy extends RecurrenceStrategy {
    private List<DayOfWeek> selectedDays;

    public WeeklyStrategy(List<DayOfWeek> selectedDays) {
        this.selectedDays = selectedDays;
    }

    @Override
    public LocalDate getNextOccurrenceDate(LocalDate lastGeneratedDate, LocalDate endDate) {
        if (lastGeneratedDate == null || selectedDays.isEmpty()) {
            return null;
        }
        
        LocalDate nextDate = lastGeneratedDate.plusDays(1);
        
        while (!nextDate.isAfter(endDate)) {
            if (selectedDays.contains(nextDate.getDayOfWeek())) {
                return nextDate;
            }
            nextDate = nextDate.plusDays(1);
        }
        
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
