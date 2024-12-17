package f24c2c1.projektkalkulering.controller;

import f24c2c1.projektkalkulering.model.Tool;
import f24c2c1.projektkalkulering.model.ToolImpl;
import f24c2c1.projektkalkulering.service.ToolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tools")
public class ToolController {

    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }
    // Default GetMapping to display the tools page
    @GetMapping
    public String showToolsPage(Model model) {

        List<Tool> tools = toolService.getAllTools();
        // Add tools and endpoint attributes to the model
        model.addAttribute("tools", tools); // Tools to display in the list
        model.addAttribute("endpoint", "tools"); // To render the tools fragment in layout.html

        return "layout"; // Return the layout template where the fragment is included
    }

    @PostMapping("/save")
    public String saveTool(@RequestParam String name, @RequestParam double value, RedirectAttributes redirectAttributes) {
        ToolImpl tool = new ToolImpl();
        tool.setName(name);
        tool.setValue(value);

        try {
            toolService.createTool(tool);
            redirectAttributes.addFlashAttribute("successMessage", "Tool added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add tool!");
        }

        return "redirect:/tools"; // Redirect to tools page after save
    }
}
