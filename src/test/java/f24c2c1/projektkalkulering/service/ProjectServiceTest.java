package f24c2c1.projektkalkulering.service;

import f24c2c1.projektkalkulering.model.Project;
import f24c2c1.projektkalkulering.model.ProjectImpl;
import f24c2c1.projektkalkulering.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    private ProjectRepository projectRepository;
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectRepository = Mockito.mock(ProjectRepository.class);
        projectService = new ProjectService(projectRepository);
    }

    @Test
    void testSaveProject() {
        Project project = new ProjectImpl();
        project.setName("New Project");
        project.setDescription("Description of new project");

        when(projectRepository.save(project)).thenReturn(1L);

        long projectId = projectService.saveProject(project);

        assertThat(projectId).isEqualTo(1L);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testUpdateProject() {
        Project project = new ProjectImpl();
        project.setId(1L);
        project.setName("Updated Project");

        doNothing().when(projectRepository).update(project);

        projectService.updateProject(project);

        verify(projectRepository, times(1)).update(project);
    }

    @Test
    void testGetProjectById() {
        Project project = new ProjectImpl();
        project.setId(1L);
        project.setName("Test Project");

        when(projectRepository.findById(1L)).thenReturn(project);

        Project foundProject = projectService.getProjectById(1L);

        assertThat(foundProject).isNotNull();
        assertThat(foundProject.getName()).isEqualTo("Test Project");

        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProjectByIdNotFound() {
        when(projectRepository.findById(99L)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> projectService.getProjectById(99L));

        assertThat(exception.getMessage()).isEqualTo("Project with ID 99 not found");
        verify(projectRepository, times(1)).findById(99L);
    }

    @Test
    void testSaveSubproject() {
        Project project = new ProjectImpl();
        project.setId(1L);

        Project subproject1 = new ProjectImpl();
        subproject1.setId(2L);
        Project subproject2 = new ProjectImpl();
        subproject2.setId(3L);

        List<Project> subprojects = Arrays.asList(subproject1, subproject2);

        doNothing().when(projectRepository).saveSubprojects(1L, subprojects);

        projectService.saveSubproject(project, subprojects);

        verify(projectRepository, times(1)).saveSubprojects(1L, subprojects);
    }

    @Test
    void testGetAllProjects() {
        Project project1 = new ProjectImpl();
        project1.setId(1L);
        project1.setName("Project 1");

        Project project2 = new ProjectImpl();
        project2.setId(2L);
        project2.setName("Project 2");

        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        List<Project> projects = projectService.getAllProjects();

        assertThat(projects).hasSize(2);
        assertThat(projects.get(0).getName()).isEqualTo("Project 1");
        assertThat(projects.get(1).getName()).isEqualTo("Project 2");

        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testDeleteProjectById() {
        long projectId = 1L;

        doNothing().when(projectRepository).deleteById(projectId);

        projectService.deleteProjectById(projectId);

        verify(projectRepository, times(1)).deleteById(projectId);
    }
}
