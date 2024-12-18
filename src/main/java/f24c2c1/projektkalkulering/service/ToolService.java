package f24c2c1.projektkalkulering.service;

import f24c2c1.projektkalkulering.model.Tool;
import f24c2c1.projektkalkulering.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {

    private final ToolRepository toolRepository;

    public ToolService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    public void createTool(Tool tool) {
        toolRepository.save(tool);
    }

    public void assignToolToTask(long taskId, long toolId) {
        toolRepository.assignToolToTask(taskId, toolId);
    }

    public List<Tool> getToolsForTask(long taskId) {
        return toolRepository.getToolsForTask(taskId);
    }

    public void deleteTool(long id) {
        toolRepository.deleteById(id);
    }

    public double calculateTotalCostForTask(long taskId) {
        return getToolsForTask(taskId).stream()
                .mapToDouble(Tool::getValue)
                .sum();
    }

    public void removeAllToolsFromTask(long taskId) {
        toolRepository.removeAllToolsFromTask(taskId);
    }
}
