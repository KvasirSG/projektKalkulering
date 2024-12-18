package f24c2c1.projektkalkulering.controller;

import f24c2c1.projektkalkulering.model.*;
import f24c2c1.projektkalkulering.service.CompetenceService;
import f24c2c1.projektkalkulering.service.ProjectService;
import f24c2c1.projektkalkulering.service.TaskService;
import f24c2c1.projektkalkulering.service.ToolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final CompetenceService competenceService;
    private final ToolService toolService;
    private final ProjectService projectService;

    // Constructor
    public TaskController(TaskService taskService, CompetenceService competenceService, ToolService toolService, ProjectService projectService)  {
        this.taskService = taskService;
        this.competenceService = competenceService;
        this.toolService = toolService;
        this.projectService = projectService;
    }

    // List all tasks
    @GetMapping
    public String listTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    // Display the task creation form tied to a sub-project
    @GetMapping("/new/{subProjectId}")
    public String showTaskForm(@PathVariable Long subProjectId, Model model) {
        TaskImpl task = new TaskImpl();
        task.setParentId(subProjectId);
        task.setStatus("NEW");
        task.setIsSubTask(false);

        // Fetch all available competences and tools
        List<Competence> allCompetences = competenceService.getAllCompetences();
        List<Tool> allTools = toolService.getAllTools();

        model.addAttribute("task", task);
        model.addAttribute("subProjectId", subProjectId);
        model.addAttribute("allCompetences", allCompetences);
        model.addAttribute("allTools", allTools);
        model.addAttribute("endpoint", "create-task");

        return "layout";
    }

    @PostMapping("/save")
    public String saveTask(
            @ModelAttribute TaskImpl task,
            @RequestParam(value = "competences", required = false) List<Long> selectedCompetenceIds,
            @RequestParam(value = "tools", required = false) List<Long> selectedToolIds,
            RedirectAttributes redirectAttributes) {
        try {
            LocalDate localDate = LocalDate.now();
            task.setCreationDate(localDate);
            // Create the task and get the task ID
            taskService.createTask(task);

            // Now assign selected competences and tools, if any were checked
            if (selectedCompetenceIds != null) {
                for (Long compId : selectedCompetenceIds) {
                    competenceService.assignCompetenceToTask(task.getId(), compId);
                }
            }

            if (selectedToolIds != null) {
                for (Long toolId : selectedToolIds) {
                    toolService.assignToolToTask(task.getId(), toolId);
                }
            }

            redirectAttributes.addFlashAttribute("successMessage", "Task created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save task!");
        }
        return "redirect:/projects/" + task.getParentId();
    }

    @GetMapping("/{id}")
    public String viewTask(@PathVariable long id, Model model) {
        Task task = taskService.getTaskById(id);

        // Fetch competences and tools assigned to this task for display
        List<Competence> assignedCompetences = competenceService.getCompetencesForTask(id);
        List<Tool> assignedTools = toolService.getToolsForTask(id);

        // Add attributes to the model
        model.addAttribute("task", task);
        model.addAttribute("assignedCompetences", assignedCompetences);
        model.addAttribute("assignedTools", assignedTools);
        model.addAttribute("endpoint", "task-details");

        return "layout";
    }


    // Show the form for editing an existing task
    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable long id, Model model) {
        Task task = taskService.getTaskById(id);
        Project project = projectService.getProjectById(task.getParentId());

        // Fetch all competences and tools
        List<Competence> allCompetences = competenceService.getAllCompetences();
        List<Tool> allTools = toolService.getAllTools();

        // Fetch the competences and tools already assigned to this task
        List<Competence> assignedCompetences = competenceService.getCompetencesForTask(id);
        List<Tool> assignedTools = toolService.getToolsForTask(id);

        model.addAttribute("subProjectId", project.getId());
        model.addAttribute("task", task);
        model.addAttribute("allCompetences", allCompetences);
        model.addAttribute("allTools", allTools);
        model.addAttribute("assignedCompetences", assignedCompetences);
        model.addAttribute("assignedTools", assignedTools);

        // endpoint for editing if needed
        model.addAttribute("endpoint", "edit-task");

        return "layout";
    }

    // Delete a task
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try {
            taskService.deleteTask(id);
            redirectAttributes.addFlashAttribute("successMessage", "Task deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete task!");
        }
        return "redirect:/tasks";
    }

    // Assign Competence to a task
    @PostMapping("/{taskId}/competences/{competenceId}")
    public String assignCompetenceToTask(@PathVariable long taskId, @PathVariable long competenceId) {
        competenceService.assignCompetenceToTask(taskId, competenceId); // Call instance method
        return "redirect:/tasks/" + taskId;
    }

    @PostMapping("/{taskId}/tools/{toolId}")
    public String assignToolToTask(@PathVariable long taskId, @PathVariable long toolId) {
        toolService.assignToolToTask(taskId, toolId);
        return "redirect:/tasks/" + taskId;
    }

    @GetMapping("/{taskId}/tools/cost")
    @ResponseBody
    public String getTotalToolCostForTask(@PathVariable long taskId) {
        double totalCost = toolService.calculateTotalCostForTask(taskId);
        return "Total Tool Cost: " + totalCost;
    }
}