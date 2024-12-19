package ma.ac.uir.devmanagement.controller;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.devmanagement.entity.Developer;
import ma.ac.uir.devmanagement.entity.ProjectManager;
import ma.ac.uir.devmanagement.entity.User;
import ma.ac.uir.devmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;  // Assuming you have a service to fetch users

    @GetMapping("/login")
    public String login() {
        return "login";  // Show login page
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        User user = userService.authenticate(username, password);

        if (user != null) {
            session.setAttribute("currentUser", user); // Store user in session

            // If the current user is a Project Manager
            if (user instanceof ProjectManager) {
                ProjectManager projectManager = (ProjectManager) user;
                session.setAttribute("managerId", projectManager.getId());  // Store managerId in session
                return "redirect:/manager/dashboard";  // Redirect to manager dashboard
            }

            // If the current user is a Developer
            if (user instanceof Developer) {
                Developer developer = (Developer) user;
                session.setAttribute("developerId", developer.getId());  // Store developerId in session
                return "redirect:/developer/dashboard";  // Redirect to developer dashboard
            }

            return "redirect:/";  // Default redirection if neither developer nor manager
        }

        return "redirect:/login";  // Redirect to login if authentication fails
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Invalidate session
        return "redirect:/login";  // Redirect to login after logout
    }

    @GetMapping("/dashboard")
    public String home(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            return "redirect:/login";  // Redirect to login if no user is found in session
        }

        // If the current user is a Project Manager
        if (currentUser instanceof ProjectManager) {
            ProjectManager projectManager = (ProjectManager) currentUser;
            session.setAttribute("managerId", projectManager.getId()); // Store managerId in session
            model.addAttribute("projects", projectManager.getProjects());  // Add project manager's projects to model
            return "redirect:/manager/dashboard"; // Redirect to manager dashboard
        }

        // If the current user is a Developer
        if (currentUser instanceof Developer) {
            Developer developer = (Developer) currentUser;
            session.setAttribute("developerId", developer.getId()); // Store developerId in session
            model.addAttribute("assignedProjects", developer.getProjects());  // Add developer's assigned projects to model
            return "redirect:/developer/dashboard"; // Redirect to developer dashboard
        }

        return "login";  // Default case if the user is neither a Developer nor a Project Manager
    }
}
