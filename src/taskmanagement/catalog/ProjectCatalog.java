package taskmanagement.catalog;

import taskmanagement.domain.Project;

import java.util.List;
import java.util.ArrayList;

public class ProjectCatalog {
    public List<Project> projects = new ArrayList<>();
    
    public void addProject(Project project) {
        boolean b = false;
        for (Project p : projects) {
            if (p.getName().equals(project.getName())) {
                b = true;
                break;
            }

        }
        if(b){project.setName(project.getName()+"1");}
    }

    public void updateProjectName(Long projectId, String name) {
        findProject(projectId).setName(name);
    }

    public void updateProjectDescription(Long projectId, String desc){
        findProject(projectId).setDescription(desc);
    }



    public Project findProject(Long projectId) {
        Project pr = null;
        for(Project p: projects){
            if(p.getProjectId()==projectId){
                pr = p;
                break;
            }
        }
        return pr;
    }
}
