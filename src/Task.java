import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Task {
    private String title;
    private String description;
    private Date dueDate;
    private String priority;
    private TaskStatus status;
    private String taskId;
    private Date creationDate;
    private Project project;
    private List<Tag> tags;
    private Recurrence recurrence;
    public Task(String title,
                String description,
                Date dueDate,
                String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.creationDate = new Date();
        this.taskId = UUID.randomUUID().toString();
    }

    public Task() {
    }

    public Task(String title){
        this.title = title;
        this.creationDate = new Date();
        this.taskId = UUID.randomUUID().toString();
    }
    public Task(String title, Project project){
        this.title = title;
        this.project = project;
        this.creationDate = new Date();
        this.taskId = UUID.randomUUID().toString();
    }

    public void changetitle(String newt){
        this.title = newt;
    }

    public void changedesc(String newdes){
        this.description = newdes;
    }

    public void changedue(Date newdue){
        this.dueDate = newdue;
    }

    public void changepriority(String newpriority){
        this.priority = newpriority;
    }

    public void changeStatus(TaskStatus stat){
        this.status = stat;
    }

    public void changeProj(Project proj){
        this.project = proj;
    }

    public void addtag(Tag t){
        this.tags.add(t);
    }

    public void removetag(Tag t){
        this.tags.remove(t);
    }

    public void setRecurrence(Recurrence r){
        this.recurrence = r;
    }

    public String gettitle(){return this.title;}

    public Date getdue(){return this.dueDate;}

    public String getdesc(){return this.description;}

    public String getprio(){return this.priority;}

    public TaskStatus getstatus(){return this.status;}

    public Project getproj(){return this.project;}

    public List<Tag> getTags(){return this.tags;}

    public Date getcreationdate(){return this.creationDate;}

    public Recurrence getrec(){return this.recurrence;}

    public String getID(){return this.taskId;}


}
