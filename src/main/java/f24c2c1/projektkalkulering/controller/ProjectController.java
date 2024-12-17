package f24c2c1.projektkalkulering.controller;

import f24c2c1.projektkalkulering.model.Project;
import f24c2c1.projektkalkulering.model.ProjectImpl;
import f24c2c1.projektkalkulering.model.Task;
import f24c2c1.projektkalkulering.service.ProjectService;
import f24c2c1.projektkalkulering.service.TaskService;
import f24c2c1.projektkalkulering.service.TimeCalculationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final TimeCalculationService timeCalculationService;

    public ProjectController(ProjectService projectService, TimeCalculationService timeCalculationService) {
        this.projectService = projectService;
        this.timeCalculationService = timeCalculationService;
    }

    /**
     * Displays the list of all projects.
     *
     * @param model the model to hold attributes
     * @return the project list view
     */
    @GetMapping
    public String listProjects(Model model) {
        List<Project> Allprojects = projectService.getAllProjects();
        List<Project> projects = new ArrayList<>();
        for (Project project : Allprojects) {
            if(!project.isSubProject()){
                projects.add(project);
            }
        }
        model.addAttribute("endpoint", "projects");
        model.addAttribute("projects", projects);
        return "layout";
    }

    /**
     * Displays the form for creating a new project.
     *
     * @param model the model to hold attributes
     * @return the project creation view
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("endpoint", "create-project-form");
        model.addAttribute("project", new ProjectImpl());
        return "layout";
    }

    /**
     * Displays the form for creating a new project.
     *
     * @param model the model to hold attributes
     * @return the project creation view
     */
    @GetMapping("/{id}/new")
    public String showSubCreateForm(@PathVariable long id,Model model) {
        model.addAttribute("id", id);
        model.addAttribute("endpoint", "create-subproject-form");
        model.addAttribute("project", new ProjectImpl());
        return "layout";
    }

    /**
     * Handles the submission of a new project.
     *
     * @param project the project to save
     * @return redirect to the project list
     */
    @PostMapping
    public String createProject(@ModelAttribute ProjectImpl project) {
        LocalDate localDate = LocalDate.now();
        project.setCreationDate(localDate);
        projectService.saveProject(project);
        return "redirect:/projects";
    }

    /**
     * Handles the submission of a new project.
     *
     * @param project the project to save
     * @return redirect to the project list
     */
    @PostMapping("/{id}/new")
    public String createSubProject(@PathVariable long id,@ModelAttribute ProjectImpl project) {
        Project project1 = projectService.getProjectById(id);
        LocalDate localDate = LocalDate.now();
        project.setCreationDate(localDate);
        project.setSubProject(true);
        long subId = projectService.saveProject(project);
        project.setId(subId);
        List<Project> subprojects = new ArrayList<>();
        subprojects.add(project);
        projectService.saveSubproject(project1,subprojects);
        return "redirect:/projects";
    }

    /**
     * Displays the form for editing an existing project.
     *
     * @param id    the project ID
     * @param model the model to hold attributes
     * @return the project edit view
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {
        Project project = projectService.getProjectById(id);
        model.addAttribute("project", project);
        return "projects/edit";
    }

    /**
     * Handles the submission of an updated project.
     *
     * @param id      the project ID
     * @param project the updated project
     * @return redirect to the project list
     */
    @PostMapping("/{id}")
    public String updateProject(@PathVariable long id, @ModelAttribute Project project) {
        project.setId(id); // Ensure the ID is set correctly
        projectService.updateProject(project);
        return "redirect:/projects";
    }

    /**
     * Deletes a project by its ID.
     *
     * @param id the project ID
     * @return redirect to the project list
     */
    @GetMapping("/{id}/delete")
    public String deleteProject(@PathVariable long id) {
        projectService.deleteProjectById(id);
        return "redirect:/projects";
    }

    double estimate(Project project) {
        return timeCalculationService.calculateEstimateForProjectTasks(project);
    }
    /**
     * Displays the detailed view of a project, including its subprojects and tasks.
     */
    @GetMapping("/{id}")
    public String viewProject(@PathVariable long id, Model model) {
        Project project = projectService.getProjectById(id);
        double totalEstimate = timeCalculationService.calculateTotalEstimateForProjectAndTasks(project);
        List<Task> tasks = project.getTasks();
        List<Project> subprojects = project.getSubProjects();

        // Prepare a hierarchical structure
        Map<Project, List<Task>> tasksBySubProject = new LinkedHashMap<>();

        for (Project subproject : subprojects) {
            tasksBySubProject.put(subproject, subproject.getTasks()); // Subproject tasks
        }

        Map<Project, Double> subprojectEstimate = new LinkedHashMap<>();
        for (Project subproject : subprojects) {
            subprojectEstimate.put(subproject, estimate(subproject));
        }
        model.addAttribute("totalEstimate", totalEstimate);
        model.addAttribute("endpoint", "project-details");
        model.addAttribute("project", project);
        model.addAttribute("tasks", tasks);
        model.addAttribute("subprojects", subprojects);
        model.addAttribute("tasksByProject", tasksBySubProject);
        model.addAttribute("subprojectEstimate", subprojectEstimate);
        return "layout";
    }


}
