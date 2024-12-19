package ma.ac.uir.devmanagement.controller;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.devmanagement.entity.Developer;
import ma.ac.uir.devmanagement.entity.Project;
import ma.ac.uir.devmanagement.entity.Evaluation;
import ma.ac.uir.devmanagement.enums.ProjectStatus;
import ma.ac.uir.devmanagement.service.DeveloperService;
import ma.ac.uir.devmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/developer")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Long developerId = (Long) session.getAttribute("developerId");
        if (developerId == null) {
            return "redirect:/auth/login";
        }

        List<Project> assignedProjects = projectService.getProjectsAssignedToDeveloper(developerId);
        int totalRatings = developerService.getTotalRatings(developerId);

        model.addAttribute("projects", assignedProjects);
        model.addAttribute("developerId", developerId);
        model.addAttribute("totalProjects", assignedProjects.size());
        model.addAttribute("totalRatings", totalRatings);

        return "developer/dashboard"; // Thymeleaf template
    }

    @GetMapping("/project/{id}")
    public String projectDetails(@PathVariable Long id, Model model, HttpSession session) {
        Long developerId = (Long) session.getAttribute("developerId");
        if (developerId == null) {
            return "redirect:/auth/login";
        }

        Project project = projectService.getProjectDetailsForDeveloper(id, developerId);
        List<String> developerNames = project.getDevelopers()
                .stream()
                .map(Developer::getUsername)
                .collect(Collectors.toList());

        Evaluation evaluation = developerService.getEvaluationForProject(developerId, id);

        model.addAttribute("project", project);
        model.addAttribute("developerNames", developerNames);
        model.addAttribute("evaluation", evaluation);

        return "developer/projectDetails"; // Thymeleaf template
    }

    @GetMapping("/projects")
    public String projectList(@RequestParam(required = false) ProjectStatus status, Model model, HttpSession session) {
        Long developerId = (Long) session.getAttribute("developerId");
        if (developerId == null) {
            return "redirect:/auth/login";
        }

        List<Project> projects;
        if (status != null) {
            projects = projectService.getProjectsByStatusForDeveloper(developerId, status);
        } else {
            projects = projectService.getProjectsAssignedToDeveloper(developerId);
        }

        model.addAttribute("projects", projects);
        assert status != null;
        model.addAttribute("status", status);

        return "developer/projects"; // Thymeleaf template
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long developerId = (Long) session.getAttribute("developerId");
        if (developerId == null) {
            return "redirect:/auth/login";
        }

        Developer developer = developerService.getDeveloperById(developerId);
        model.addAttribute("developer", developer);

        return "developer/profile"; // Thymeleaf template
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam String username,
                                @RequestParam String email,
                                @RequestParam List<String> skills,
                                HttpSession session) {
        Long developerId = (Long) session.getAttribute("developerId");
        if (developerId == null) {
            return "redirect:/auth/login";
        }

        developerService.updateDeveloperProfile(developerId, username, email, skills);
        return "redirect:/developer/profile";
    }
}
