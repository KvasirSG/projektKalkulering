package f24c2c1.projektkalkulering.service;

import f24c2c1.projektkalkulering.model.Competence;
import f24c2c1.projektkalkulering.repository.CompetenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetenceService {

    private final CompetenceRepository competenceRepository;

    public CompetenceService(CompetenceRepository competenceRepository) {
        this.competenceRepository = competenceRepository;
    }

    // Get all competences
    public List<Competence> getAllCompetences() {
        return competenceRepository.findAll();
    }

    // Create a new competence
    public void createCompetence(Competence competence) {
        competenceRepository.save(competence);
    }

    // Assign a competence to a task
    public void assignCompetenceToTask(long taskId, long competenceId) {
        competenceRepository.assignCompetenceToTask(taskId, competenceId);
    }

    // Get competences assigned to a task
    public List<Competence> getCompetencesForTask(long taskId) {
        return competenceRepository.getCompetencesForTask(taskId);
    }

    public void removeAllCompetencesFromTask(long taskId) {
        competenceRepository.removeAllCompetencesFromTask(taskId);
    }

    // Delete a competence
    public void deleteCompetence(long id) {
        competenceRepository.deleteById(id);
    }
}
