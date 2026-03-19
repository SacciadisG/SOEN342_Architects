import java.util.List;
import java.util.ArrayList;

public class ProjectCatalog {

    private List<Project> projects;

    public ProjectCatalog(){
        this.projects = new ArrayList<Project>();
    }

    public void addProject(Project p){
        this.projects.add(p);
    }

    public void deleteProject(Project p){
        this.projects.remove(p);
    }
}
