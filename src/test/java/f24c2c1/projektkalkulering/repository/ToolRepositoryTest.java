package f24c2c1.projektkalkulering.repository;

import f24c2c1.projektkalkulering.model.Tool;
import f24c2c1.projektkalkulering.model.ToolImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(ToolRepository.class)
class ToolRepositoryTest {

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setupDatabase() {
        // Create necessary tables
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS tools (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    tool_value DOUBLE
                );
                """);

        // We assume there's a tasks table since we're assigning tools to tasks
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS tasks (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL
                );
                """);

        // Mapping table for many-to-many relationship between tasks and tools
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS task_tools (
                    task_id BIGINT NOT NULL,
                    tool_id BIGINT NOT NULL,
                    FOREIGN KEY (task_id) REFERENCES tasks(id),
                    FOREIGN KEY (tool_id) REFERENCES tools(id)
                );
                """);

        // Clean up data before each test
        jdbcTemplate.execute("DELETE FROM task_tools");
        jdbcTemplate.execute("DELETE FROM tools");
        jdbcTemplate.execute("DELETE FROM tasks");

        // Insert at least one task and one tool for reference
        jdbcTemplate.update("INSERT INTO tasks (id, name, creation_date) VALUES (1, 'Test Task', ?)",
                java.sql.Date.valueOf(LocalDate.now()));
        jdbcTemplate.update("INSERT INTO tools (id, name, tool_value) VALUES (1, 'Hammer', 29.99)");
        jdbcTemplate.update("INSERT INTO tools (id, name, tool_value) VALUES (2, 'Screwdriver', 9.99)");


    }

    @Test
    void testFindAll() {
        List<Tool> tools = toolRepository.findAll();
        // We inserted two tools in setup: Hammer and Screwdriver
        assertThat(tools).hasSize(2);
        assertThat(tools).extracting("name").containsExactlyInAnyOrder("Hammer", "Screwdriver");
    }

    @Test
    void testSave() {
        ToolImpl newTool = new ToolImpl();
        newTool.setName("Wrench");
        newTool.setValue(15.50);
        toolRepository.save(newTool);

        List<Tool> tools = toolRepository.findAll();
        assertThat(tools).hasSize(3).extracting("name").contains("Wrench");
    }

    @Test
    void testDeleteById() {
        // Before: We have 2 tools: Hammer(id=1), Screwdriver(id=2)
        toolRepository.deleteById(1L);

        List<Tool> tools = toolRepository.findAll();
        assertThat(tools).hasSize(1);
        assertThat(tools.get(0).getName()).isEqualTo("Screwdriver");
    }

    @Test
    void testAssignToolToTaskAndGetToolsForTask() {
        // Assign tool with id=1 (Hammer) to task with id=1
        toolRepository.assignToolToTask(1L, 1L);

        // Fetch tools for task_id=1
        List<Tool> taskTools = toolRepository.getToolsForTask(1L);
        assertThat(taskTools).hasSize(1);
        assertThat(taskTools.get(0).getName()).isEqualTo("Hammer");

        // Assign another tool (id=2) to the same task
        toolRepository.assignToolToTask(1L, 2L);

        taskTools = toolRepository.getToolsForTask(1L);
        assertThat(taskTools).hasSize(2).extracting("name").contains("Screwdriver");
    }

    @Test
    void testRemoveAllToolsFromTask() {
        // Assign two tools to the task
        toolRepository.assignToolToTask(1L, 1L);
        toolRepository.assignToolToTask(1L, 2L);

        // Ensure they are assigned
        List<Tool> taskTools = toolRepository.getToolsForTask(1L);
        assertThat(taskTools).hasSize(2);

        // Remove all tools from task
        toolRepository.removeAllToolsFromTask(1L);

        // After removal, we expect no tools for this task
        taskTools = toolRepository.getToolsForTask(1L);
        // This might fail if removeAllToolsFromTask isn't implemented correctly in ToolRepository
        // Once you fix it (by adding `jdbcTemplate.update("DELETE FROM task_tools WHERE task_id = ?", taskId);` inside that method),
        // this assertion will pass.
        assertThat(taskTools).isEmpty();
    }
}
