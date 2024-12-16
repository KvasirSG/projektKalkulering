package f24c2c1.projektkalkulering.repository;

import f24c2c1.projektkalkulering.model.Competence;
import f24c2c1.projektkalkulering.model.CompetenceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CompetenceRepository {

    private final JdbcTemplate jdbcTemplate;

    public CompetenceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Competence> COMPETENCE_ROW_MAPPER = (rs, rowNum) -> {
        Competence competence = new CompetenceImpl();
        competence.setId(rs.getLong("id"));
        competence.setName(rs.getString("name"));
        return competence;
    };

    public List<Competence> findAll() {
        return jdbcTemplate.query("SELECT * FROM competences", COMPETENCE_ROW_MAPPER);
    }

    public void save(Competence competence) {
        jdbcTemplate.update("INSERT INTO competences (name) VALUES (?)", competence.getName());
    }
}
