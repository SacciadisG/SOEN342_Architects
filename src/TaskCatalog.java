import java.sql.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TaskCatalog {

    private List<Task> tasks;

    public TaskCatalog(){
        this.tasks = new ArrayList<Task>();
    }

    public void addTask(Task t){
        this.tasks.add(t);
    }

    public void removeTask(Task t){
        this.tasks.remove(t);
    }

    public List<Task> filterbyproj(Project proj){
        return tasks.stream()
                .filter(t -> t.getproj().equals(proj))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksByTag(String tag){
        return tasks.stream()
                .filter(t -> t.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    public List<Task> serachbytitlekey(String keyword){
        String k = keyword.toLowerCase();
        return tasks.stream()
                .filter(t -> t.gettitle().toLowerCase().contains(k) ||
                        t.getdesc().toLowerCase().contains(k))
                .collect(Collectors.toList());


    }

}
