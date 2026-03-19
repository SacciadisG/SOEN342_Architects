import java.util.List;

public class Project {
    private String title;
    private String description;
    private List<Task> tasks;

    public void settitle(String title){
        this.title = title;
    }

    public void setdesc(String desc){
        this.description = desc;
    }

    public void addtask(Task t){
        this.tasks.add(t);
    }

    public void deletetask(Task t){
        this.tasks.remove(t);
    }

    public String gettitle(){return this.title;}

    public String getdesc(){return this.description;}

    public List<Task> gettasks(){return this.tasks;}
}
