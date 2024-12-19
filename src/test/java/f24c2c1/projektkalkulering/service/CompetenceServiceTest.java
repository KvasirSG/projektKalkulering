package f24c2c1.projektkalkulering.service;

import f24c2c1.projektkalkulering.model.Competence;
import f24c2c1.projektkalkulering.model.CompetenceImpl;
import f24c2c1.projektkalkulering.repository.CompetenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CompetenceServiceTest {

    private CompetenceRepository competenceRepository;
    private CompetenceService competenceService;

    @BeforeEach
    void setUp() {
        competenceRepository = Mockito.mock(CompetenceRepository.class);
        competenceService = new CompetenceService(competenceRepository);
    }

    @Test
    void testGetAllCompetences() {
        Competence competence1 = new CompetenceImpl();
        competence1.setId(1L);
        competence1.setName("Java Development");

        Competence competence2 = new CompetenceImpl();
        competence2.setId(2L);
        competence2.setName("Database Management");

        when(competenceRepository.findAll()).thenReturn(Arrays.asList(competence1, competence2));

        List<Competence> competences = competenceService.getAllCompetences();

        assertThat(competences).hasSize(2);
        assertThat(competences.get(0).getName()).isEqualTo("Java Development");
        assertThat(competences.get(1).getName()).isEqualTo("Database Management");

        verify(competenceRepository, times(1)).findAll();
    }

    @Test
    void testCreateCompetence() {
        Competence competence = new CompetenceImpl();
        competence.setId(1L);
        competence.setName("Spring Framework");

        doNothing().when(competenceRepository).save(competence);

        competenceService.createCompetence(competence);

        verify(competenceRepository, times(1)).save(competence);
    }

    @Test
    void testAssignCompetenceToTask() {
        long taskId = 1L;
        long competenceId = 2L;

        doNothing().when(competenceRepository).assignCompetenceToTask(taskId, competenceId);

        competenceService.assignCompetenceToTask(taskId, competenceId);

        verify(competenceRepository, times(1)).assignCompetenceToTask(taskId, competenceId);
    }

    @Test
    void testGetCompetencesForTask() {
        long taskId = 1L;

        Competence competence = new CompetenceImpl();
        competence.setId(1L);
        competence.setName("Project Management");

        when(competenceRepository.getCompetencesForTask(taskId)).thenReturn(Collections.singletonList(competence));

        List<Competence> competences = competenceService.getCompetencesForTask(taskId);

        assertThat(competences).hasSize(1);
        assertThat(competences.get(0).getName()).isEqualTo("Project Management");

        verify(competenceRepository, times(1)).getCompetencesForTask(taskId);
    }

    @Test
    void testRemoveAllCompetencesFromTask() {
        long taskId = 1L;

        doNothing().when(competenceRepository).removeAllCompetencesFromTask(taskId);

        competenceService.removeAllCompetencesFromTask(taskId);

        verify(competenceRepository, times(1)).removeAllCompetencesFromTask(taskId);
    }

    @Test
    void testDeleteCompetence() {
        long competenceId = 1L;

        doNothing().when(competenceRepository).deleteById(competenceId);

        competenceService.deleteCompetence(competenceId);

        verify(competenceRepository, times(1)).deleteById(competenceId);
    }
}
