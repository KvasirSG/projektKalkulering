package f24c2c1.projektkalkulering.repository;

import f24c2c1.projektkalkulering.model.Tool;
import f24c2c1.projektkalkulering.model.ToolImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ToolRepository {

    private final JdbcTemplate jdbcTemplate;

    public ToolRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Tool> TOOL_ROW_MAPPER = (rs, rowNum) -> {
        Tool tool = new ToolImpl();
        tool.setId(rs.getLong("id"));
        tool.setName(rs.getString("name"));
        tool.setValue(rs.getDouble("tool_value"));
        return tool;
    };

    public List<Tool> findAll() {
        String sql = "SELECT * FROM tools";
        return jdbcTemplate.query(sql, TOOL_ROW_MAPPER);
    }

    public void save(Tool tool) {
        String sql = "INSERT INTO tools (name, tool_value) VALUES (?, ?)";
        jdbcTemplate.update(sql, tool.getName(), tool.getValue());
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM tools WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void assignToolToTask(long taskId, long toolId) {
        String sql = "INSERT INTO task_tools (task_id, tool_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, taskId, toolId);
    }

    public List<Tool> getToolsForTask(long taskId) {
        String sql = "SELECT t.* FROM tools t JOIN task_tools tt ON t.id = tt.tool_id WHERE tt.task_id = ?";
        return jdbcTemplate.query(sql, TOOL_ROW_MAPPER, taskId);
    }
}
