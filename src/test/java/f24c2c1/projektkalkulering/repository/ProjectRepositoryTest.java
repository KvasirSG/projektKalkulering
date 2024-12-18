package f24c2c1.projektkalkulering.repository;

import f24c2c1.projektkalkulering.model.*;
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
@Import(ProjectRepository.class)
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setupDatabase() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS projects (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    description TEXT,
                    creation_date DATE,
                    start_date DATE,
                    end_date DATE,
                    creator_id BIGINT,
                    client_id BIGINT,
                    is_subproject BOOLEAN DEFAULT FALSE,
                    FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE SET NULL,
                    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE SET NULL
                );
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS tasks (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    description VARCHAR(255),
                    creation_date DATE,
                    estimate INT,
                    start_date DATE,
                    end_date DATE,
                    status VARCHAR(50),
                    is_subtask BOOLEAN,
                    project_id BIGINT,
                    FOREIGN KEY (project_id) REFERENCES projects(id)
                );
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS clients (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    contact_name VARCHAR(255),
                    address VARCHAR(255),
                    city VARCHAR(255),
                    zip VARCHAR(10),
                    phone VARCHAR(20),
                    email VARCHAR(255)
                );
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    email VARCHAR(255),
                    name VARCHAR(255),
                    password VARCHAR(255),
                    role VARCHAR(50)
                );
                """);

        jdbcTemplate.execute("DELETE FROM tasks");
        jdbcTemplate.execute("DELETE FROM projects");
        jdbcTemplate.execute("DELETE FROM clients");
        jdbcTemplate.execute("DELETE FROM users");

        jdbcTemplate.update("INSERT INTO users (id, email, name, password, role) VALUES (1, 'user1@example.com', 'User One', 'password', 'ROLE_USER')");
        jdbcTemplate.update("INSERT INTO clients (id, name, email, address, city, zip, phone) VALUES (1, 'Client One', 'client1@example.com', '123 Main St', 'City', '12345', '555-5555')");
    }

    @Test
    void testSaveAndFindById() {
        ProjectImpl project = new ProjectImpl();
        project.setName("Test Project");
        project.setDescription("Test Description");
        project.setCreationDate(LocalDate.now());

        // Mock creator and client
        UserImpl mockCreator = new UserImpl();
        mockCreator.setId(1L);
        project.setCreator(mockCreator);

        ClientImpl mockClient = new ClientImpl();
        mockClient.setId(1L);
        project.setClient(mockClient);

        project.setStartDate(LocalDate.now().plusDays(1));
        project.setEndDate(LocalDate.now().plusDays(10));
        project.setSubProject(false);

        Long projectId = projectRepository.save(project);
        Project savedProject = projectRepository.findById(projectId);

        assertThat(savedProject).isNotNull();
        assertThat(savedProject.getName()).isEqualTo("Test Project");
        assertThat(savedProject.getCreator().getId()).isEqualTo(1L);
        assertThat(savedProject.getClient().getId()).isEqualTo(1L);
    }

    @Test
    void testUpdateProject() {
        ProjectImpl project = new ProjectImpl();
        project.setName("Initial Project");
        project.setDescription("Initial Description");
        project.setCreationDate(LocalDate.now());

        // Mock creator and client
        UserImpl mockCreator = new UserImpl();
        mockCreator.setId(1L);
        project.setCreator(mockCreator);

        ClientImpl mockClient = new ClientImpl();
        mockClient.setId(1L);
        project.setClient(mockClient);

        Long projectId = projectRepository.save(project);

        project.setId(projectId);
        project.setName("Updated Project");
        project.setDescription("Updated Description");
        project.setStartDate(LocalDate.now().plusDays(2));
        project.setEndDate(LocalDate.now().plusDays(12));
        projectRepository.update(project);

        Project updatedProject = projectRepository.findById(projectId);
        assertThat(updatedProject.getName()).isEqualTo("Updated Project");
        assertThat(updatedProject.getDescription()).isEqualTo("Updated Description");
        assertThat(updatedProject.getCreator().getId()).isEqualTo(1L);
        assertThat(updatedProject.getClient().getId()).isEqualTo(1L);
    }

    @Test
    void testFindAll() {
        ProjectImpl project1 = new ProjectImpl();
        project1.setName("Project 1");
        project1.setDescription("Description 1");
        project1.setCreationDate(LocalDate.now());

        // Mock creator and client
        UserImpl mockCreator = new UserImpl();
        mockCreator.setId(1L);
        project1.setCreator(mockCreator);

        ClientImpl mockClient = new ClientImpl();
        mockClient.setId(1L);
        project1.setClient(mockClient);

        project1.setStartDate(LocalDate.now().plusDays(1));
        project1.setEndDate(LocalDate.now().plusDays(10));
        projectRepository.save(project1);

        ProjectImpl project2 = new ProjectImpl();
        project2.setName("Project 2");
        project2.setDescription("Description 2");
        project2.setCreationDate(LocalDate.now());

        // Mock creator and client
        project2.setCreator(mockCreator);
        project2.setClient(mockClient);

        project2.setStartDate(LocalDate.now().plusDays(5));
        project2.setEndDate(LocalDate.now().plusDays(15));
        projectRepository.save(project2);

        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(2);
    }

    @Test
    void testDeleteById() {
        ProjectImpl project = new ProjectImpl();
        project.setName("Project to Delete");
        project.setDescription("Description to Delete");
        project.setCreationDate(LocalDate.now());

        // Mock creator and client
        UserImpl mockCreator = new UserImpl();
        mockCreator.setId(1L);
        project.setCreator(mockCreator);

        ClientImpl mockClient = new ClientImpl();
        mockClient.setId(1L);
        project.setClient(mockClient);

        project.setStartDate(LocalDate.now().plusDays(1));
        project.setEndDate(LocalDate.now().plusDays(10));
        Long projectId = projectRepository.save(project);

        projectRepository.deleteById(projectId);
        List<Project> projects = projectRepository.findAll();

        assertThat(projects).isEmpty();
    }
}
