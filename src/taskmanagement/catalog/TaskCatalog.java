package taskmanagement.catalog;

import taskmanagement.domain.Task;
import taskmanagement.enums.StatusEnum;


import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class TaskCatalog {
    public List<Task> tasks = new ArrayList<>();
    public void addTask(Task task) {
        tasks.add(task);
    }
    public void deleteTask(Task task){
        tasks.remove(task);
    }

    public void updateTask(Long taskId, Object details) {

    }

    public Task findTask(Long taskId) {
        Task t =null;
        for(Task tk: tasks){
            if(tk.getTaskId()==taskId){
                t = tk;
                break;
            }
        }
        return t;
    }

    public List<Task> findTasksByCriteria(Object criteria) {
        return null;
    }


    public List<Task> displayTasksNoFilter(){

        int n = tasks.size();
        for (int i = 0; i < n - 1; i++) {
            int earliestIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (tasks.get(j).getDueDate().isBefore(tasks.get(earliestIndex).getDueDate())) {
                    earliestIndex = j;
                }
            }
            Task temp = tasks.get(earliestIndex);
            tasks.set(earliestIndex, tasks.get(i));
            tasks.set(i, temp);
        }
        List<Task> tk = new ArrayList<>();
        for (Task t: tasks){
            if(t.getStatus().equals(StatusEnum.OPEN)){
                tk.add(t);
            }
        }
        return tk;

    }
    public List<Task> filterTasksByTitle(String title){
        List<Task> sametitletasks = new ArrayList<>();
        for  (Task task: tasks){
            if(task.getTitle().contains(title)){sametitletasks.add(task);}
        }
        return sametitletasks;
    }

    public List<Task> filterTaskByStatus(StatusEnum status){
        List<Task> samestatustasks = new ArrayList<>();
        for (Task task: tasks){
            if(task.getStatus().equals(status)){samestatustasks.add(task);}
        }
        return samestatustasks;
    }

    public List<Task> filterTaskByDueDateRange(LocalDate startrange, LocalDate endrange){
        List<Task> taskswithinrange = new ArrayList<>();
        for (Task task: tasks){
            if(( task.getDueDate().isBefore(endrange) && task.getDueDate().isAfter(startrange))
                    || (task.getDueDate().isEqual(endrange) || task.getDueDate().isEqual(startrange) )){
                taskswithinrange.add(task);
            }
        }
        return taskswithinrange;
    }

    public List<Task> filterTaskByCreationDateRange(LocalDate startrange, LocalDate endrange){
        List<Task> taskswithinrange = new ArrayList<>();
        for (Task task: tasks){
            if(( task.getCreationDate().isBefore(endrange) && task.getCreationDate().isAfter(startrange))
                    || (task.getCreationDate().isEqual(endrange) || task.getCreationDate().isEqual(startrange) )){
                taskswithinrange.add(task);
            }
        }
        return taskswithinrange;
    }

    public List<Task> filterTaskByDayofWeek(String day){
        List<Task> samedayofweektasks = new ArrayList<>();
        for (Task task: tasks){
            if(task.getDueDate().getDayOfWeek().name().equalsIgnoreCase(day)){
                samedayofweektasks.add(task);
            }
        }
        return samedayofweektasks;
    }



}

