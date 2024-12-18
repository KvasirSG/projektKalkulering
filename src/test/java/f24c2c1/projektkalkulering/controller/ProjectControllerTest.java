package f24c2c1.projektkalkulering.controller;

import f24c2c1.projektkalkulering.model.Project;
import f24c2c1.projektkalkulering.model.ProjectImpl;
import f24c2c1.projektkalkulering.model.Task;
import f24c2c1.projektkalkulering.service.ProjectService;
import f24c2c1.projektkalkulering.service.TimeCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private TimeCalculationService timeCalculationService;

    private ProjectImpl testProject;
    private ProjectImpl testSubProject;

    @BeforeEach
    void setUp() {
        testProject = new ProjectImpl();
        testProject.setId(1L);
        testProject.setName("Main Project");
        testProject.setDescription("Main project desc");
        testProject.setCreationDate(LocalDate.now());
        testProject.setSubProject(false);

        testSubProject = new ProjectImpl();
        testSubProject.setId(2L);
        testSubProject.setName("Sub Project");
        testSubProject.setDescription("Sub project desc");
        testSubProject.setCreationDate(LocalDate.now());
        testSubProject.setSubProject(true);
    }

    @Test
    void testListProjects() throws Exception {
        List<Project> allProjects = Arrays.asList(testProject, testSubProject);
        when(projectService.getAllProjects()).thenReturn(allProjects);

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("projects", "endpoint"))
                .andExpect(model().attribute("endpoint", "projects"))
                // Only non-sub projects should be listed
                .andExpect(model().attribute("projects", hasSize(1)))
                .andExpect(view().name("layout"));

        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/projects/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("endpoint", "project"))
                .andExpect(model().attribute("endpoint", "create-project-form"))
                .andExpect(view().name("layout"));
    }

    @Test
    void testShowSubCreateForm() throws Exception {
        when(projectService.getProjectById(1L)).thenReturn(testProject);

        mockMvc.perform(get("/projects/1/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("id", "endpoint", "project"))
                .andExpect(model().attribute("endpoint", "create-subproject-form"))
                .andExpect(view().name("layout"));
    }

    @Test
    void testCreateProject() throws Exception {
        doAnswer(invocation -> {
            ProjectImpl p = invocation.getArgument(0);
            p.setId(10L);
            return null;
        }).when(projectService).saveProject(ArgumentMatchers.any(ProjectImpl.class));

        mockMvc.perform(post("/projects")
                        .param("name", "New Project")
                        .param("description", "New Project Desc")
                        .param("startDate", "")
                        .param("endDate", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

        verify(projectService, times(1)).saveProject(ArgumentMatchers.any(ProjectImpl.class));
    }

    @Test
    void testCreateSubProject() throws Exception {
        when(projectService.getProjectById(1L)).thenReturn(testProject);
        doAnswer(invocation -> {
            ProjectImpl p = invocation.getArgument(0);
            p.setId(11L);
            return 11L;
        }).when(projectService).saveProject(ArgumentMatchers.any(ProjectImpl.class));

        mockMvc.perform(post("/projects/1/new")
                        .param("name", "SubProjectName")
                        .param("description", "Sub desc"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

        verify(projectService, times(1)).getProjectById(1L);
        verify(projectService, times(1)).saveProject(ArgumentMatchers.any(ProjectImpl.class));
        verify(projectService, times(1)).saveSubproject(eq(testProject), anyList());
    }

    @Test
    void testShowEditForm() throws Exception {
        when(projectService.getProjectById(1L)).thenReturn(testProject);

        mockMvc.perform(get("/projects/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attribute("project", hasProperty("name", org.hamcrest.Matchers.is("Main Project"))))
                .andExpect(view().name("projects/edit"));
    }

    @Test
    void testUpdateProject() throws Exception {
        mockMvc.perform(post("/projects/1")
                        .param("name", "Updated Project")
                        .param("description", "Updated Desc"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

        verify(projectService, times(1)).updateProject(ArgumentMatchers.any(Project.class));
    }

    @Test
    void testDeleteProject() throws Exception {
        mockMvc.perform(get("/projects/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

        verify(projectService, times(1)).deleteProjectById(1L);
    }

    @Test
    void testViewProject() throws Exception {
        // Setup
        List<Task> tasks = Collections.emptyList();
        List<Project> subprojects = Collections.singletonList(testSubProject);

        // Instead of mocking getters, set them directly:
        testProject.setTasks(tasks);
        testProject.setSubProjects(subprojects);

        when(projectService.getProjectById(1L)).thenReturn(testProject);
        when(timeCalculationService.calculateTotalEstimateForProjectAndTasks(testProject)).thenReturn(100.0);
        // For subproject estimates:
        when(timeCalculationService.calculateEstimateForProjectTasks(testSubProject)).thenReturn(20.0);

        mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("totalEstimate", "endpoint", "project", "tasks", "subprojects", "tasksByProject", "subprojectEstimate"))
                .andExpect(model().attribute("endpoint", "project-details"))
                .andExpect(model().attribute("totalEstimate", 100.0))
                .andExpect(view().name("layout"));

        verify(projectService, times(1)).getProjectById(1L);
        verify(timeCalculationService, times(1)).calculateTotalEstimateForProjectAndTasks(testProject);
    }
}