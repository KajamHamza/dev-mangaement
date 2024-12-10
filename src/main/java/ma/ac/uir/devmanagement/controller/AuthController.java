package ma.ac.uir.devmanagement.controller;


import jakarta.servlet.http.HttpSession;
import ma.ac.uir.devmanagement.entity.Developer;
import ma.ac.uir.devmanagement.entity.ProjectManager;
import ma.ac.uir.devmanagement.entity.User;
import ma.ac.uir.devmanagement.enums.Role;
import ma.ac.uir.devmanagement.repository.DeveloperRepository;
import ma.ac.uir.devmanagement.repository.ProjectManagerRepository;
import ma.ac.uir.devmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    // Login Page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Login Submission
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }
        session.setAttribute("currentUser", user);
        return "redirect:/home";
    }

    // Register Page
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // Register Submission
    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam Role role,
            Model model) {
        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("error", "Email already exists.");
            return "register";
        }

        User user;
        if (role == Role.DEVELOPER) {
            user = new Developer(username, password, email, role);
            developerRepository.save((Developer) user);
        } else if (role == Role.PROJECT_MANAGER) {
            user = new ProjectManager(username, password, email, role);
            projectManagerRepository.save((ProjectManager) user);
        } else {
            model.addAttribute("error", "Invalid role selected.");
            return "register";
        }

        return "redirect:/auth/login";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
