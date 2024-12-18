package f24c2c1.projektkalkulering.controller;

import f24c2c1.projektkalkulering.model.Tool;
import f24c2c1.projektkalkulering.model.ToolImpl;
import f24c2c1.projektkalkulering.service.ToolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ToolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToolService toolService;

    private ToolImpl testTool;

    @BeforeEach
    void setUp() {
        testTool = new ToolImpl();
        testTool.setId(1L);
        testTool.setName("Test Tool");
        testTool.setValue(99.99);
    }

    @Test
    void testShowToolsPage() throws Exception {
        List<Tool> tools = Collections.singletonList(testTool);
        when(toolService.getAllTools()).thenReturn(tools);

        mockMvc.perform(get("/tools"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tools", "endpoint"))
                .andExpect(model().attribute("tools", tools))
                .andExpect(model().attribute("endpoint", "tools"))
                .andExpect(view().name("layout"));

        verify(toolService, times(1)).getAllTools();
    }

    @Test
    void testSaveToolSuccess() throws Exception {
        // Simulate successful tool creation (no exception thrown)
        doNothing().when(toolService).createTool(ArgumentMatchers.any(ToolImpl.class));

        mockMvc.perform(post("/tools/save")
                        .param("name", "NewTool")
                        .param("value", "49.99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(redirectedUrl("/tools"));

        verify(toolService, times(1)).createTool(ArgumentMatchers.any(ToolImpl.class));
    }

    @Test
    void testSaveToolFailure() throws Exception {
        // Simulate failure by throwing an exception
        doThrow(new RuntimeException("DB error")).when(toolService).createTool(ArgumentMatchers.any(ToolImpl.class));

        mockMvc.perform(post("/tools/save")
                        .param("name", "FailTool")
                        .param("value", "100.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"))
                .andExpect(redirectedUrl("/tools"));

        verify(toolService, times(1)).createTool(ArgumentMatchers.any(ToolImpl.class));
    }
}
