/*
 * ===================================================================================
 * File:        ProjectImpl.java
 * Description: Implementation of the Project interface, representing a project entity
 *              with attributes like name, description, dates, creator, tasks, and subprojects.
 *
 * Author:      Kenneth (KvasirSG)
 * Created:     2024-11-28
 * Updated:     2024-11-28
 * Version:     1.0
 *
 * License:     MIT License
 *
 * Notes:       - This class is used in conjunction with ProjectRepository for database
 *                operations.
 *              - Tasks and subprojects are stored in separate tables with relations to
 *                the main project entity.
 * ===================================================================================
 */
package f24c2c1.projektkalkulering.model;

import java.util.Date;
import java.util.List;

public class ProjectImpl implements Project {
    private long id;
    private String name;
    private String description;
    private Date creationDate;
    private Date startDate;
    private Date endDate;
    private User creator;
    private boolean isSubproject;
    private List<Task> tasks;
    private List<Project> subprojects;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public User getCreator() {
        return creator;
    }

    @Override
    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Override
    public Boolean isSubProject() {
        return isSubproject;
    }

    @Override
    public void setSubProject(Boolean subProject) {
        isSubproject = subProject;
    }

    @Override
    public List<Project> getSubProjects() {
        return subprojects;
    }

    @Override
    public void setSubProjects(List<Project> subProjects) {
        this.subprojects = subProjects;
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
