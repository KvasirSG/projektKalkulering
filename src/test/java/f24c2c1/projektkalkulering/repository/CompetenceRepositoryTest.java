package f24c2c1.projektkalkulering.repository;

import f24c2c1.projektkalkulering.model.Competence;
import f24c2c1.projektkalkulering.model.CompetenceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(CompetenceRepository.class) // Import the repository to test
class CompetenceRepositoryTest {

    @Autowired
    private CompetenceRepository competenceRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setupDatabase() {
        // Ensure tables are created only if they do not exist
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS competences (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL
                );
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS task_competences (
                    task_id BIGINT NOT NULL,
                    competence_id BIGINT NOT NULL,
                    PRIMARY KEY (task_id, competence_id),
                    FOREIGN KEY (competence_id) REFERENCES competences(id)
                );
                """);

        // Clean up and insert initial test data
        jdbcTemplate.execute("DELETE FROM competences");
        jdbcTemplate.execute("DELETE FROM task_competences");

        jdbcTemplate.update("INSERT INTO competences (id, name) VALUES (1, 'Competence 1')");
        jdbcTemplate.update("INSERT INTO competences (id, name) VALUES (2, 'Competence 2')");
    }

    @Test
    void testFindAll() {
        // Act
        List<Competence> competences = competenceRepository.findAll();

        // Assert
        assertThat(competences).hasSize(2);
        assertThat(competences.get(0).getName()).isEqualTo("Competence 1");
        assertThat(competences.get(1).getName()).isEqualTo("Competence 2");
    }

    @Test
    void testAssignCompetenceToTask() {
        // Arrange
        long taskId = 1L;
        long competenceId = 1L; // Ensure this ID exists in the 'competences' table

        // Act
        competenceRepository.assignCompetenceToTask(taskId, competenceId);

        // Assert
        List<Competence> assignedCompetences = competenceRepository.getCompetencesForTask(taskId);
        assertThat(assignedCompetences).hasSize(1);
        assertThat(assignedCompetences.get(0).getName()).isEqualTo("Competence 1");
    }

    @Test
    void testGetCompetencesForTask() {
        // Arrange
        long taskId = 1L;
        competenceRepository.assignCompetenceToTask(taskId, 1L);
        competenceRepository.assignCompetenceToTask(taskId, 2L);

        // Act
        List<Competence> assignedCompetences = competenceRepository.getCompetencesForTask(taskId);

        // Assert
        assertThat(assignedCompetences).hasSize(2);
        assertThat(assignedCompetences.get(0).getName()).isEqualTo("Competence 1");
        assertThat(assignedCompetences.get(1).getName()).isEqualTo("Competence 2");
    }

    @Test
    void testRemoveAllCompetencesFromTask() {
        // Arrange
        long taskId = 1L;
        competenceRepository.assignCompetenceToTask(taskId, 1L);
        competenceRepository.assignCompetenceToTask(taskId, 2L);

        // Act
        competenceRepository.removeAllCompetencesFromTask(taskId);

        // Assert
        List<Competence> assignedCompetences = competenceRepository.getCompetencesForTask(taskId);
        assertThat(assignedCompetences).isEmpty();
    }
}
