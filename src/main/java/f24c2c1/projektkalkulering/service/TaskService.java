/*
 * ===================================================================================
 * File:        TaskService.java
 * Description: Service layer for managing Task entities. Encapsulates business
 *              logic and provides an abstraction over the TaskRepository for
 *              handling CRUD operations.
 *
 * Author:      Sebastian (Duofour)
 * Contributor: Kenneth (KvasirSG)
 * Created:     2024-11-28
 * Updated:     2024-12-15
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

import f24c2c1.projektkalkulering.model.Task;
import f24c2c1.projektkalkulering.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    private static final Map<String, Integer> THRESHOLDS = Map.of(
            "Developer", 8,
            "Tester", 6
    );

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
        taskRepository.save(task);
    }

    public void updateTask(Task task) {
        taskRepository.update(task);
    }

    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    public Map<String, Boolean> checkOverburdening(long projectId) {
        Map<String, Integer> totalHoursByCompetency = taskRepository.getTotalHoursByCompetency(projectId);
        Map<String, Boolean> isOverburdened = new HashMap<>();

        for (Map.Entry<String, Integer> entry : totalHoursByCompetency.entrySet()) {
            String competency = entry.getKey();
            int hours = entry.getValue();
            int threshold = THRESHOLDS.getOrDefault(competency, 8); // Default 8 hours/day

            isOverburdened.put(competency, hours > threshold);
        }

        return isOverburdened;
    }
}
