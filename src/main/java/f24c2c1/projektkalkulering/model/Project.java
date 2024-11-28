package f24c2c1.projektkalkulering.model;

import java.util.Date;
import java.util.List;

public interface Project {
    long getId();
    void setId(long id);
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    Date getCreationDate();
    void setCreationDate(Date creationDate);
    Date getStartDate();
    void setStartDate(Date startDate);
    Date getEndDate();
    void setEndDate(Date endDate);
    User getCreator();
    void setCreator(User creator);
    Boolean isSubProject();
    void setSubProject(Boolean subProject);
    List<Project> getSubProjects();
    void setSubProjects(List<Project> subProjects);
    List<Task> getTasks();
    void setTasks(List<Task> tasks);
}
