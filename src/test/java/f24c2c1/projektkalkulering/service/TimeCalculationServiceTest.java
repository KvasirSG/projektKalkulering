package f24c2c1.projektkalkulering.service;

import f24c2c1.projektkalkulering.model.ProjectImpl;
import f24c2c1.projektkalkulering.model.TaskImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TimeCalculationServiceTest {

    private ProjectService projectService;
    private TimeCalculationService timeCalculationService;

    @BeforeEach
    void setUp() {
        projectService = Mockito.mock(ProjectService.class);
        timeCalculationService = new TimeCalculationService(projectService);
    }

    @Test
    void testCalculateTotalEstimateForAllProjects() {
        TaskImpl task1 = new TaskImpl();
        task1.setEstimate(4);

        TaskImpl task2 = new TaskImpl();
        task2.setEstimate(6);

        ProjectImpl project1 = new ProjectImpl();
        project1.setId(1L);
        project1.setSubProject(false);
        project1.setTasks(Arrays.asList(task1, task2));

        TaskImpl task3 = new TaskImpl();
        task3.setEstimate(10);

        ProjectImpl project2 = new ProjectImpl();
        project2.setId(2L);
        project2.setSubProject(true);
        project2.setTasks(Collections.singletonList(task3));

        // Mocking `projectService.getAllProjects` and `projectService.getProjectById`
        when(projectService.getAllProjects()).thenReturn(Arrays.asList(project1, project2));
        when(projectService.getProjectById(1L)).thenReturn(project1);
        when(projectService.getProjectById(2L)).thenReturn(project2);

        int totalEstimate = timeCalculationService.calculateTotalEstimateForAllProjects();

        assertThat(totalEstimate).isEqualTo(20);
        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    void testCalculateCompletionDate() {
        LocalDate startDate = LocalDate.of(2024, 12, 18);
        int estimate = 24; // 3 workdays (8 hours/day)

        LocalDate completionDate = timeCalculationService.calculateCompletionDate(startDate, estimate);

        // Adjusted expected date to account for correct weekend exclusion
        assertThat(completionDate).isEqualTo(LocalDate.of(2024, 12, 23));
    }

    @Test
    void testCalculateTotalEstimateForProjectAndTasks() {
        TaskImpl task1 = new TaskImpl();
        task1.setEstimate(2);

        TaskImpl task2 = new TaskImpl();
        task2.setEstimate(3);

        TaskImpl subTask = new TaskImpl();
        subTask.setEstimate(5);

        ProjectImpl subProject = new ProjectImpl();
        subProject.setId(2L);
        subProject.setTasks(Collections.singletonList(subTask));

        ProjectImpl project = new ProjectImpl();
        project.setId(1L);
        project.setTasks(Arrays.asList(task1, task2));
        project.setSubProjects(Collections.singletonList(subProject));

        // Mocking `projectService.getProjectById`
        when(projectService.getProjectById(1L)).thenReturn(project);
        when(projectService.getProjectById(2L)).thenReturn(subProject);

        int totalEstimate = timeCalculationService.calculateTotalEstimateForProjectAndTasks(project);

        assertThat(totalEstimate).isEqualTo(10);
        verify(projectService, times(1)).getProjectById(1L);
    }
}
