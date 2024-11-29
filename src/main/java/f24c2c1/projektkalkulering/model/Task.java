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
    List<Task> getSubTasks();
    void setSubTasks(List<Task> subTasks);
}