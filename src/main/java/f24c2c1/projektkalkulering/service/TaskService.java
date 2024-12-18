/*
 * ===================================================================================
 * File:        TaskService.java
 * Description: Service layer for managing Task entities. Encapsulates business
 *              logic and provides an abstraction over the TaskRepository for
 *              handling CRUD operations.
 *
 * Author:      Sebastian (Duofour)
 * Created:     2024-11-28
 * Updated:     2024-11-29
 * Version:     1.0
 *
 * License:     MIT License
 *
 * Notes:       - This service serves as the intermediary between controllers and
 *                the TaskRepository, ensuring that business rules are applied
 *                consistently.
 *              - Exceptions for missing tasks (e.g., Task not found) can be added
 *                for improved error handling.
 * ===================================================================================
 */
package f24c2c1.projektkalkulering.service;

import f24c2c1.projektkalkulering.model.Competence;
import f24c2c1.projektkalkulering.model.CompetenceImpl;
import f24c2c1.projektkalkulering.model.Task;
import f24c2c1.projektkalkulering.repository.TaskRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(long id) {
        return taskRepository.findById(id);
    }
    public void createTask(Task task) {
        long id = taskRepository.save(task);
        task.setId(id);
    }


    public void updateTask(Task task) {
        taskRepository.update(task);
    }

    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

}
