package taskmanagement.strategy;

import taskmanagement.domain.Task;

public abstract class RecurrenceStrategy {
    
    public abstract Task generateReoccuringTask();
}
