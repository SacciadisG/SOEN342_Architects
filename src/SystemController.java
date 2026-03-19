import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class SystemController {
    private ProjectCatalog projs;
    private TaskCatalog tasks;
    private ActivityLog acts;

    public SystemController(){
        this.projs = new ProjectCatalog();
        this.tasks = new TaskCatalog();
        this.acts = new ActivityLog();

    }
    public LocalDate parser(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        return LocalDate.parse(date, formatter);
    }



}
