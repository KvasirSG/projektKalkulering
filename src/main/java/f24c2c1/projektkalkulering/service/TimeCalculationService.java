package f24c2c1.projektkalkulering.service;

import f24c2c1.projektkalkulering.model.Project;
import f24c2c1.projektkalkulering.model.Task;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for calculating time estimates and completion dates for projects and tasks.
 */
@Service
public class TimeCalculationService {

    private final ProjectService projectService;

    /**
     * Constructor for TimeCalculationService.
     *
     * @param projectService the service for managing projects.
     */
    public TimeCalculationService(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Calculates the total time estimate for all projects, including sub-projects.
     *
     * @return the total time estimate in hours.
     */
    public double calculateTotalEstimateForAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        double totalEstimate = 0;

        for (Project project : projects) {
            if (project.isSubProject()) {
                totalEstimate += calculateTotalEstimateForProjectAndTasks(project);
            }
        }

        for (Project project : projects) {
            if (!project.isSubProject()) {
                totalEstimate += calculateTotalEstimateForProjectAndTasks(project);
            }
        }

        return totalEstimate;
    }

    /**
     * Calculates the total time estimate for a project and all its sub-projects and tasks.
     *
     * @param project the main project.
     * @return the total time estimate in hours.
     */
    public double calculateTotalEstimateForProjectAndTasks(Project project) {
        double totalEstimate = calculateEstimateForProjectTasks(project);

        for (Project subProject : project.getSubProjects()) {
            totalEstimate += calculateEstimateForProjectTasks(subProject);
        }

        return totalEstimate;
    }

    /**
     * Calculates the total time estimate for all tasks in a given project.
     *
     * @param project the project whose tasks need to be estimated.
     * @return the total time estimate in hours.
     */
    public double calculateEstimateForProjectTasks(Project project) {
        double taskEstimate = 0;
        Project detailedProject = projectService.getProjectById(project.getId());

        for (Task task : detailedProject.getTasks()) {
            taskEstimate += task.getEstimate();
        }

        return taskEstimate;
    }

    /**
     * Calculates the completion date for a project or task based on the start date and total time estimate.
     *
     * @param startDate the starting date of the project or task.
     * @param estimate  the total time estimate in hours.
     * @return the calculated completion date.
     */
    public LocalDate calculateCompletionDate(LocalDate startDate, double estimate) {
        final int dailyWorkHours = 8;
        int workdaysNeeded = (int) Math.ceil(estimate / dailyWorkHours);
        LocalDate completionDate = startDate;

        while (workdaysNeeded > 0) {
            completionDate = completionDate.plusDays(1);
            DayOfWeek dayOfWeek = completionDate.getDayOfWeek();

            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                workdaysNeeded--;
            }
        }

        return completionDate;
    }
}
