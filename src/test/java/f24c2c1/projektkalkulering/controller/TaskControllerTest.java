package f24c2c1.projektkalkulering.controller;

import f24c2c1.projektkalkulering.model.*;
import f24c2c1.projektkalkulering.service.CompetenceService;
import f24c2c1.projektkalkulering.service.ProjectService;
import f24c2c1.projektkalkulering.service.TaskService;
import f24c2c1.projektkalkulering.service.ToolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private CompetenceService competenceService;

    @MockBean
    private ToolService toolService;

    @MockBean
    private ProjectService projectService;

    private TaskImpl testTask;
    private Project testProject;
    private Competence testCompetence;
    private Tool testTool;

    @BeforeEach
    void setUp() {
        // Prepare some test data
        testTask = new TaskImpl();
        testTask.setId(1L);
        testTask.setName("Test Task");
        testTask.setDescription("Description");
        testTask.setCreationDate(LocalDate.now());
        testTask.setEstimate(10);
        testTask.setIsSubTask(false);
        testTask.setParentId(1L);

        testProject = new ProjectImpl();
        testProject.setId(1L);
        testProject.setName("Test Project");

        testCompetence = new CompetenceImpl();
        testCompetence.setId(100L);
        testCompetence.setName("Test Competence");

        testTool = new ToolImpl();
        testTool.setId(200L);
        testTool.setName("Test Tool");
        testTool.setValue(50.0);
    }

    @Test
    void testShowTaskForm() throws Exception {
        List<Competence> competences = Collections.singletonList(testCompetence);
        List<Tool> tools = Collections.singletonList(testTool);
        when(competenceService.getAllCompetences()).thenReturn(competences);
        when(toolService.getAllTools()).thenReturn(tools);

        mockMvc.perform(get("/tasks/new/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("task", "subProjectId", "allCompetences", "allTools", "endpoint"))
                .andExpect(model().attribute("subProjectId", 1L))
                .andExpect(model().attribute("endpoint", "create-task"))
                .andExpect(view().name("layout"));
    }

    @Test
    void testSaveTask() throws Exception {
        // Mock services
        doAnswer(invocation -> {
            Task t = invocation.getArgument(0);
            t.setId(2L);
            return null;
        }).when(taskService).createTask(ArgumentMatchers.any(Task.class));

        mockMvc.perform(post("/tasks/save")
                        .param("name", "New Task")
                        .param("description", "New Desc")
                        .param("estimate", "20")
                        .param("parentId", "2")
                        .param("status", "NEW")
                        .param("isSubTask", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/2"));

        verify(taskService, times(1)).createTask(ArgumentMatchers.any(Task.class));
    }

    @Test
    void testViewTask() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(testTask);
        when(competenceService.getCompetencesForTask(1L)).thenReturn(Collections.singletonList(testCompetence));
        when(toolService.getToolsForTask(1L)).thenReturn(Collections.singletonList(testTool));

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("task", "assignedCompetences", "assignedTools", "endpoint"))
                .andExpect(model().attribute("endpoint", "task-details"))
                .andExpect(model().attribute("task", hasProperty("name", is("Test Task"))))
                .andExpect(view().name("layout"));
    }

    @Test
    void testShowEditTaskForm() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(testTask);
        when(projectService.getProjectById(1L)).thenReturn(testProject);
        when(competenceService.getAllCompetences()).thenReturn(Collections.singletonList(testCompetence));
        when(toolService.getAllTools()).thenReturn(Collections.singletonList(testTool));
        when(competenceService.getCompetencesForTask(1L)).thenReturn(Collections.singletonList(testCompetence));
        when(toolService.getToolsForTask(1L)).thenReturn(Collections.singletonList(testTool));

        mockMvc.perform(get("/tasks/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("editMode", true))
                .andExpect(model().attribute("endpoint", "edit-task"))
                .andExpect(view().name("layout"));
    }

    @Test
    void testUpdateTask() throws Exception {
        when(taskService.getTaskById(2L)).thenReturn(testTask);

        mockMvc.perform(post("/tasks/update/2")
                        .param("name", "Updated Task")
                        .param("description", "Updated Desc")
                        .param("estimate", "15")
                        .param("parentId", "1")
                        .param("status", "IN_PROGRESS")
                        .param("isSubTask", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1"));

        verify(taskService, times(1)).updateTask(ArgumentMatchers.any(Task.class));
        verify(competenceService, times(1)).removeAllCompetencesFromTask(2L);
        verify(toolService, times(1)).removeAllToolsFromTask(2L);
    }

    @Test
    void testDeleteTask() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(testTask);

        mockMvc.perform(post("/tasks/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1"));

        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void testAssignCompetenceToTask() throws Exception {
        mockMvc.perform(post("/tasks/1/competences/100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks/1"));

        verify(competenceService, times(1)).assignCompetenceToTask(1L, 100L);
    }

    @Test
    void testAssignToolToTask() throws Exception {
        mockMvc.perform(post("/tasks/1/tools/200"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks/1"));

        verify(toolService, times(1)).assignToolToTask(1L, 200L);
    }

    @Test
    void testGetTotalToolCostForTask() throws Exception {
        when(toolService.calculateTotalCostForTask(1L)).thenReturn(100.0);

        mockMvc.perform(get("/tasks/1/tools/cost"))
                .andExpect(status().isOk())
                .andExpect(content().string("Total Tool Cost: 100.0"));

        verify(toolService, times(1)).calculateTotalCostForTask(1L);
    }
}
