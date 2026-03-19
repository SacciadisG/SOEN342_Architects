import java.util.Date;

public class SubTask extends Task {
    private String title;
    private String description;
    private Date dueDate;
    private String priority;

    public SubTask(String title, String description, Date dueDate, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;

    }

}
