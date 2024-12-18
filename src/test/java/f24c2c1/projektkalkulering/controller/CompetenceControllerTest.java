package f24c2c1.projektkalkulering.controller;

import f24c2c1.projektkalkulering.model.Competence;
import f24c2c1.projektkalkulering.model.CompetenceImpl;
import f24c2c1.projektkalkulering.service.CompetenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CompetenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompetenceService competenceService;

    private CompetenceImpl testCompetence;

    @BeforeEach
    void setUp() {
        testCompetence = new CompetenceImpl();
        testCompetence.setId(1L);
        testCompetence.setName("Test Competence");
    }

    @Test
    void testShowCompetenceForm() throws Exception {
        List<Competence> competences = Collections.singletonList(testCompetence);
        when(competenceService.getAllCompetences()).thenReturn(competences);

        mockMvc.perform(get("/competences"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("competences", "endpoint"))
                .andExpect(model().attribute("competences", competences))
                .andExpect(model().attribute("endpoint", "competences"))
                .andExpect(view().name("layout"));

        verify(competenceService, times(1)).getAllCompetences();
    }

    @Test
    void testSaveCompetence() throws Exception {
        doNothing().when(competenceService).createCompetence(ArgumentMatchers.any(Competence.class));

        mockMvc.perform(post("/competences/save")
                        .param("name", "New Competence"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/competences"));

        verify(competenceService, times(1)).createCompetence(ArgumentMatchers.any(Competence.class));
    }
}
