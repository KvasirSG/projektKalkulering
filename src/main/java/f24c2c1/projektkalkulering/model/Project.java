/*
 * ===================================================================================
 * File:        Project.java
 * Description: Defines the contract for a Project entity, specifying the attributes
 *              and behaviors required to manage a project, including its metadata,
 *              creator, tasks, and subprojects.
 *
 * Author:      Kenneth (KvasirSG)
 * Created:     2024-11-28
 * Updated:     2024-11-28
 * Version:     1.0
 *
 * License:     MIT License
 *
 * Notes:       - This interface is designed to represent a project entity in the
 *                application.
 *              - Concrete implementations should ensure proper management of the
 *                relationships between a project, its tasks, and its subprojects.
 *              - Dates should be handled carefully to ensure consistent time zone
 *                and format management across implementations.
 * ===================================================================================
 */
package f24c2c1.projektkalkulering.model;

import java.util.Date;
import java.util.List;
/**
 * Interface defining the essential properties and behaviors of a Project entity.
 */
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
