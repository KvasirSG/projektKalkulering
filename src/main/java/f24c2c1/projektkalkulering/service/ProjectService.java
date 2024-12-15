/*
 * ===================================================================================
 * File:        ProjectService.java
 * Description: Service layer for managing Project entities and related calculations.
 *              Provides business logic for project CRUD operations, time estimates,
 *              and daily work hour calculations.
 *
 * Author:      Kenneth (KvasirSG)
 * Created:     2024-11-28
 * Updated:     2024-12-15
 * Version:     1.0
 *
 * License:     MIT License
 *
 * Notes:       - Implements business logic on top of repositories for projects
 *                and tasks.
 *              - Includes utility methods for time-related calculations like
 *                total project hours and daily work hour requirements.
 *              - Ensure to handle exceptions and invalid input for project dates
 *                and dependencies.
 * ===================================================================================
 */

package f24c2c1.projektkalkulering.service;


import f24c2c1.projektkalkulering.model.Project;
import f24c2c1.projektkalkulering.repository.ProjectRepository;
import f24c2c1.projektkalkulering.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    /**
     * Saves a new project with its related entities (tasks, subprojects).
     *
     * @param project the project to save
     */
    @Transactional
    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    /**
     * Updates an existing project with its related entities.
     *
     * @param project the project to update
     */
    @Transactional
    public void updateProject(Project project) {
        projectRepository.update(project);
    }

    /**
     * Retrieves a project by its ID.
     *
     * @param id the project ID
     * @return the project, or an exception if not found
     */
    public Project getProjectById(long id) {
        return Optional.ofNullable(projectRepository.findById(id))
                .orElseThrow(() -> new RuntimeException("Project with ID " + id + " not found"));
    }

    /**
     * Retrieves all projects.
     *
     * @return a list of all projects
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Deletes a project by its ID.
     *
     * @param id the project ID
     */
    @Transactional
    public void deleteProjectById(long id) {
        projectRepository.deleteById(id);
    }

    public int getTotalTimeEstimate(long projectId) {
        return taskRepository.calculateTotalEstimateForProject(projectId);
    }

    public int calculateDailyHours(long projectId) {
        int totalHours = getTotalTimeEstimate(projectId);
        Map<String, Date> dates = projectRepository.getProjectDates(projectId);

        // Convert java.util.Date to LocalDate
        LocalDate start = dates.get("startDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = dates.get("endDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long totalDays = ChronoUnit.DAYS.between(start, end);

        if (totalDays <= 0) throw new IllegalArgumentException("Invalid project duration");

        return (int) Math.ceil((double) totalHours / totalDays);
    }


}
