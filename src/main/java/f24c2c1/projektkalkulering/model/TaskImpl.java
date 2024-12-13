/*
 * ===================================================================================
 * File:        TaskImpl.java
 * Description: Concrete implementation of the Task interface, representing an
 *              individual task within a project. Includes attributes for task
 *              metadata, status, time estimates, and subtask relationships.
 *
 * Author:      Sebastian (Duofour)
 * Created:     2024-11-28
 * Updated:     2024-11-29
 * Version:     1.0
 *
 * License:     MIT License
 *
 * Notes:       - This implementation provides default behavior for the methods
 *                defined in the Task interface.
 *              - Subtasks are managed via a list, allowing hierarchical task
 *                structures.
 *              - Time fields (startDate, endDate) and estimate are represented
 *                in hours and should be validated in business logic.
 * ===================================================================================
 */
package f24c2c1.projektkalkulering.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskImpl implements Task {
    private long id;
    private String name;
    private String description;
    private Date creationDate;
    private int estimate; // in hours
    private Date startDate;
    private Date endDate;
    private String status; // e.g., "Not Started", "In Progress", "Completed"
    private Boolean isSubTask;
    private List<Task> subTasks;

    public TaskImpl() {
        this.subTasks = new ArrayList<>();
    }

    // Getters and Setters for the interface methods
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
    public int getEstimate() {
        return estimate;
    }

    @Override
    public void setEstimate(int estimate) {
        this.estimate = estimate;
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
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Boolean getIsSubTask() {
        return isSubTask;
    }

    @Override
    public void setIsSubTask(Boolean isSubTask) {
        this.isSubTask = isSubTask;
    }

    @Override
    public List<Task> getSubTasks() {
        return subTasks;
    }

    @Override
    public void setSubTasks(List<Task> subTasks) {
        this.subTasks = subTasks;
    }
}