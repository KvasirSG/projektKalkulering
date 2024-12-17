package f24c2c1.projektkalkulering.service;

import f24c2c1.projektkalkulering.model.Task;
import f24c2c1.projektkalkulering.model.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostCalculatorService {

    // Services used to fetch task details, tool data, and time calculations
    private final ToolService toolService;

    /**
     * Constructor-based dependency injection to initialize the required services.
     * @param toolService            Service to fetch tool data associated with tasks.
     */
    public CostCalculatorService(ToolService toolService) {
        this.toolService = toolService;
    }

    /**
     * Calculates the total cost for a given task based on tools and estimated task time.
     *
     * @param task The task for which the cost needs to be calculated.
     * @return The calculated cost for the task.
     */
    public double calculateCost(Task task) {
        // Fetch all tools associated with the given task
        List<Tool> tools = toolService.getToolsForTask(task.getId());

        // Total number of hours in 30 days (fixed value)
        int totalHours = 30 * 24; // 30 days * 24 hours per day

        // Variable to accumulate the monthly cost of tools
        double costMonth = 0;

        // Sum up the value of all tools used in this task
        for (Tool tool : tools) {
            costMonth += tool.getValue();
        }

        // Calculate the hourly cost of tools
        double costPerHour = costMonth / totalHours;

        // Final cost = hourly cost * estimated hours for the task
        return costPerHour * task.getEstimate();
    }
}
