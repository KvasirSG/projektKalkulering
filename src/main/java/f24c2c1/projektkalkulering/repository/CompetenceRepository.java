package f24c2c1.projektkalkulering.repository;

import f24c2c1.projektkalkulering.model.Competence;
import f24c2c1.projektkalkulering.model.CompetenceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompetenceRepository {

    private final JdbcTemplate jdbcTemplate;

    public CompetenceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for Competence
    private final RowMapper<Competence> COMPETENCE_ROW_MAPPER = (rs, rowNum) -> {
        Competence competence = new CompetenceImpl();
        competence.setId(rs.getLong("id"));
        competence.setName(rs.getString("name"));
        return competence;
    };

    // Fetch all competences
    public List<Competence> findAll() {
        String sql = "SELECT * FROM competences";
        return jdbcTemplate.query(sql, COMPETENCE_ROW_MAPPER);
    }

    // Save a new competence
    public void save(Competence competence) {
        String sql = "INSERT INTO competences (name) VALUES (?)";
        jdbcTemplate.update(sql, competence.getName());
    }

    // Delete a competence
    public void deleteById(long id) {
        String sql = "DELETE FROM competences WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Assign a competence to a task
    public void assignCompetenceToTask(long taskId, long competenceId) {
        String sql = "INSERT INTO task_competences (task_id, competence_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, taskId, competenceId);
    }

    // Get competences assigned to a specific task
    public List<Competence> getCompetencesForTask(long taskId) {
        String sql = "SELECT c.* FROM competences c " +
                "JOIN task_competences tc ON c.id = tc.competence_id " +
                "WHERE tc.task_id = ?";
        return jdbcTemplate.query(sql, COMPETENCE_ROW_MAPPER, taskId);
    }
}
