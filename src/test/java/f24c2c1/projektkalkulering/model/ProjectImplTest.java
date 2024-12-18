package f24c2c1.projektkalkulering.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectImplTest {

    @Test
    void testSettersAndGetters() {
        // Arrange
        ProjectImpl project = new ProjectImpl();
        long id = 1L;
        String name = "Test Project";
        String description = "A test project description";
        LocalDate creationDate = LocalDate.of(2024, 12, 1);
        LocalDate startDate = LocalDate.of(2024, 12, 10);
        LocalDate endDate = LocalDate.of(2024, 12, 20);
        User creator = new UserImpl();
        Client client = new ClientImpl();
        boolean isSubproject = true;
        List<Task> tasks = new ArrayList<>();
        List<Project> subprojects = new ArrayList<>();

        // Act
        project.setId(id);
        project.setName(name);
        project.setDescription(description);
        project.setCreationDate(creationDate);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setCreator(creator);
        project.setClient(client);
        project.setSubProject(isSubproject);
        project.setTasks(tasks);
        project.setSubProjects(subprojects);

        // Assert
        assertEquals(id, project.getId(), "ID should match");
        assertEquals(name, project.getName(), "Name should match");
        assertEquals(description, project.getDescription(), "Description should match");
        assertEquals(creationDate, project.getCreationDate(), "Creation date should match");
        assertEquals(startDate, project.getStartDate(), "Start date should match");
        assertEquals(endDate, project.getEndDate(), "End date should match");
        assertEquals(creator, project.getCreator(), "Creator should match");
        assertEquals(client, project.getClient(), "Client should match");
        assertEquals(isSubproject, project.isSubProject(), "Subproject flag should match");
        assertEquals(tasks, project.getTasks(), "Tasks should match");
        assertEquals(subprojects, project.getSubProjects(), "Subprojects should match");
    }

    @Test
    void testDefaultValues() {
        // Arrange
        ProjectImpl project = new ProjectImpl();

        // Assert
        assertEquals(0L, project.getId(), "Default ID should be 0");
        assertNull(project.getName(), "Default name should be null");
        assertNull(project.getDescription(), "Default description should be null");
        assertNull(project.getCreationDate(), "Default creation date should be null");
        assertNull(project.getStartDate(), "Default start date should be null");
        assertNull(project.getEndDate(), "Default end date should be null");
        assertNull(project.getCreator(), "Default creator should be null");
        assertNull(project.getClient(), "Default client should be null");
        assertFalse(project.isSubProject(), "Default isSubProject should be false");
        assertNull(project.getTasks(), "Default tasks should be null");
        assertNull(project.getSubProjects(), "Default subprojects should be null");
    }
}
