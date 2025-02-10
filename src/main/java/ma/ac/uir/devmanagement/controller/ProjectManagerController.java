package ma.ac.uir.devmanagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.devmanagement.entity.Developer;
import ma.ac.uir.devmanagement.entity.Project;
import ma.ac.uir.devmanagement.entity.ProjectManager;
import ma.ac.uir.devmanagement.service.DeveloperService;
import ma.ac.uir.devmanagement.service.ProjectManagerService;

@Controller
@RequestMapping("/manager")
public class ProjectManagerController {

    @Autowired
    private ProjectManagerService projectManagerService;

    @Autowired
    private DeveloperService developerService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long managerId = (Long) session.getAttribute("managerId");

        if (managerId == null) {
            return "redirect:/auth/login";  // Redirect to login if managerId is not found in the session
        }

        List<Project> projects = projectManagerService.getProjectsCreatedByManager(managerId);

        // Calculate project stats by status
        long completedProjects = projects.stream().filter(p -> "Completed".equalsIgnoreCase(String.valueOf(p.getStatus()))).count();
        long activeProjects = projects.stream().filter(p -> "Active".equalsIgnoreCase(String.valueOf(p.getStatus()))).count();
        long pendingProjects = projects.stream().filter(p -> "Pending".equalsIgnoreCase(String.valueOf(p.getStatus()))).count();

        model.addAttribute("projects", projects);
        model.addAttribute("stats", List.of(
                Map.of("label", "Completed Projects", "value", completedProjects),
                Map.of("label", "Active Projects", "value", activeProjects),
                Map.of("label", "Pending Projects", "value", pendingProjects)
        ));

        return "manager/dashboard"; // Dashboard Thymeleaf template
    }

    @GetMapping("/projects")
    public String projects(HttpSession session, Model model) {
        Long managerId = (Long) session.getAttribute("managerId");

        if (managerId == null) {
            return "redirect:/auth/login";  // Redirect to login if managerId is not found in the session
        }

        List<Project> projects = projectManagerService.getProjectsCreatedByManager(managerId);
        model.addAttribute("projects", projects);
        return "manager/projects";  // Manager's project list page
    }

    @GetMapping("/project/{id}")
    public String projectDetails(@PathVariable Long id, HttpSession session, Model model) {
        Long managerId = (Long) session.getAttribute("managerId");

        if (managerId == null) {
            return "redirect:/auth/login";  // Redirect to login if managerId is not found in the session
        }

        Project project = projectManagerService.getProjectById(id);
        long teamSize = project.getDevelopers().size(); // Assuming `getDevelopers()` returns a list of assigned developers
        List<Developer> availableDevelopers = developerService.getAllDevelopers();

        model.addAttribute("project", project);
        model.addAttribute("teamSize", teamSize);
        model.addAttribute("availableDevelopers", availableDevelopers);

        return "manager/projectDetails";  // Project details page
    }

    @GetMapping("/createProject")
    public String createProjectForm(HttpSession session, Model model) {
        Long managerId = (Long) session.getAttribute("managerId");

        if (managerId == null) {
            return "redirect:/auth/login";  // Redirect to login if managerId is not found in the session
        }

        model.addAttribute("project", new Project());
        model.addAttribute("skills", developerService.getAllSkills()); // Add skill list to model
        return "manager/createProject";  // Project creation form page
    }

    @PostMapping("/createProject")
    public String createProject(@ModelAttribute Project project, @RequestParam List<String> skills, HttpSession session) {
        Long managerId = (Long) session.getAttribute("managerId");

        if (managerId == null) {
            return "redirect:/auth/login";  // Redirect to login if managerId is not found in the session
        }
        project.setRequiredSkills(skills);
        projectManagerService.createProject(project, managerId);
        return "redirect:/manager/dashboard";  // Redirect to the manager dashboard
    }

    @PostMapping("/assignDeveloper/{projectId}")
    public String assignDevelopersToProject(@PathVariable Long projectId, @RequestParam List<Long> developerIds) {
        projectManagerService.assignDevelopersToProject(projectId, developerIds);
        return "redirect:/manager/projects/" + projectId;
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam Long projectId, @RequestParam String status, HttpSession session) {
        Long managerId = (Long) session.getAttribute("managerId");
        if (managerId == null) {
            return "redirect:/auth/login";
        }
        projectManagerService.updateProjectStatus(projectId, status);
        return "redirect:/manager/project/" + projectId;
    }

    @PostMapping("/evaluateDeveloper")
    public String evaluateDeveloper(@RequestParam Long projectId, @RequestParam Long developerId,
                                    @RequestParam int stars, @RequestParam String feedback, HttpSession session) {

        Long managerId = (Long) session.getAttribute("managerId");
        if (managerId == null) {
            return "redirect:/auth/login";
        }
        projectManagerService.evaluateDeveloper(projectId, developerId, stars, feedback);
        return "redirect:/manager/project/" + projectId;
    }

    @GetMapping("/search")
    public String searchDevelopers(@RequestParam(required = false) List<String> skills, Model model, HttpSession session) {
        Long managerId = (Long) session.getAttribute("managerId");
        if (managerId == null) {
            return "redirect:/auth/login";
        }
        List<Developer> developers;

        if (skills == null || skills.isEmpty()) {
            developers = developerService.getAllDevelopers();
        } else {
            developers = developerService.findDevelopersBySkills(skills);
        }

        model.addAttribute("developers", developers);
        model.addAttribute("skills", skills);
        return "manager/search";
    }

    @GetMapping("/settings")
    public String settings(Model model, HttpSession session) {
        Long managerId = (Long) session.getAttribute("managerId");
        if (managerId == null) {
            return "redirect:/auth/login";
        }

        ProjectManager manager = projectManagerService.getManagerById(managerId);
        model.addAttribute("manager", manager);
        return "manager/settings";
    }

    @PostMapping("/settings/updateProfile")
    public String updateProfile(@ModelAttribute ProjectManager manager, HttpSession session) {
        Long managerId = (Long) session.getAttribute("managerId");
        if (managerId == null) {
            return "redirect:/auth/login";
        }
        projectManagerService.updateManagerProfile(manager);
        return "redirect:/manager/settings?managerId=" + manager.getId();
    }


}
