package f24c2c1.projektkalkulering.controller;

import f24c2c1.projektkalkulering.model.Task;
import f24c2c1.projektkalkulering.model.TaskImpl;
import f24c2c1.projektkalkulering.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // List all tasks
    @GetMapping
    public String listTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    // Show the form for creating a new task
    @GetMapping("/new/{procID}")
    public String showNewTaskForm(@PathVariable long procID,Model model) {
        Task task = new TaskImpl();
        task.setParentId(procID);

        model.addAttribute("task",task);
        return "tasks/form";
    }

    // Save a new task or update an existing one
    @PostMapping("/save")
    public String saveTask(@ModelAttribute Task task, RedirectAttributes redirectAttributes) {
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    // Show the form for editing an existing task
    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Task task = taskService.getTaskById(id);
            model.addAttribute("task", task);
            return "tasks/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Task not found!");
            return "redirect:/tasks";
        }
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
        taskService.assignCompetenceToTask(taskId, competenceId);
        return "redirect:/tasks/" + taskId;
    }

}
