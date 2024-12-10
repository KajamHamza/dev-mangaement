package ma.ac.uir.devmanagement.controller;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.devmanagement.entity.Developer;
import ma.ac.uir.devmanagement.entity.ProjectManager;
import ma.ac.uir.devmanagement.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Home Page
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/auth/login";
        }

        if (currentUser instanceof Developer) {
            model.addAttribute("projects", ((Developer) currentUser).getProjects());
            return "developer-home";
        } else if (currentUser instanceof ProjectManager) {
            model.addAttribute("projects", ((ProjectManager) currentUser).getProjects());
            return "project-manager-home";
        }

        return "login";
    }
}
