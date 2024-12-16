package f24c2c1.projektkalkulering.service;

import f24c2c1.projektkalkulering.model.Project;
import f24c2c1.projektkalkulering.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Saves a new project with its related entities (tasks, subprojects).
     *
     * @param project the project to save
     */
    @Transactional
    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    /**
     * Updates an existing project with its related entities.
     *
     * @param project the project to update
     */
    @Transactional
    public void updateProject(Project project) {
        projectRepository.update(project);
    }

    /**
     * Retrieves a project by its ID.
     *
     * @param id the project ID
     * @return the project, or an exception if not found
     */
    public Project getProjectById(long id) {
        return Optional.ofNullable(projectRepository.findById(id))
                .orElseThrow(() -> new RuntimeException("Project with ID " + id + " not found"));
    }

    /**
     * Retrieves all projects.
     *
     * @return a list of all projects
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Deletes a project by its ID.
     *
     * @param id the project ID
     */
    @Transactional
    public void deleteProjectById(long id) {
        projectRepository.deleteById(id);
    }
}
