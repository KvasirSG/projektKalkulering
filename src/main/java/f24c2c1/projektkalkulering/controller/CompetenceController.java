package f24c2c1.projektkalkulering.controller;

import f24c2c1.projektkalkulering.model.Competence;
import f24c2c1.projektkalkulering.model.CompetenceImpl;
import f24c2c1.projektkalkulering.service.CompetenceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/competences")
public class CompetenceController {

    private final CompetenceService competenceService;

    public CompetenceController(CompetenceService competenceService) {
        this.competenceService = competenceService;
    }

    // Show the form for adding a competence
    @GetMapping
    public String showCompetenceForm(Model model) {
        List<Competence> competences = competenceService.getAllCompetences();
        model.addAttribute("competences", competences);
        model.addAttribute("endpoint", "competences");
        return "layout"; // Reuse layout.html
    }

    // Save a new competence
    @PostMapping("/save")
    public String saveCompetence(@RequestParam("name") String name) {
        Competence competence = new CompetenceImpl();
        competence.setName(name);
        competenceService.createCompetence(competence);

        return "redirect:/competences";
    }
}
