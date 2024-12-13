/*
 * ===================================================================================
 * File:        Task.java
 * Description: Defines the contract for a Task entity with methods for managing
 *              task attributes such as name, description, dates, and subtasks.
 *
 * Author:      Kenneth (KvasirSG)
 * Created:     2024-11-28
 * Updated:     2024-11-28
 * Version:     1.0
 *
 * License:     MIT License
 *
 * Notes:       - Implementations of this interface are used in the Project module.
 *              - Ensure all attributes are validated when implementing this interface.
 * ===================================================================================
 */
package f24c2c1.projektkalkulering.model;

import java.util.Date;
import java.util.List;

public interface Task {
    long getId();
    void setId(long id);
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    Date getCreationDate();
    void setCreationDate(Date creationDate);
    int getEstimate();
    void setEstimate(int estimate);
    Date getStartDate();
    void setStartDate(Date startDate);
    Date getEndDate();
    void setEndDate(Date endDate);
    String getStatus();
    void setStatus(String status);
    Boolean getIsSubTask();
    void setIsSubTask(Boolean isSubTask);
    long getParentId();
    void setParentId(long parentId);
    List<Task> getSubTasks();
    void setSubTasks(List<Task> subTasks);
}
