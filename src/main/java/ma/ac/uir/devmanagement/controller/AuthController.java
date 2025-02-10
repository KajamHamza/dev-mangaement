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
        return "redirect:/dashboard";
    }

    // registerrrrrrrrr Page
    @GetMapping("/registerrrrrrrr")
    public String registerrrrrrrrPage() {
        return "registerrrrrrrr";
    }

    // registerrrrrrrr Submission
    @PostMapping("/registerrrrrrrr")
    public String registerrrrrrrrr(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam Role role,
            Model model) {
        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("error", "Email already exists.");
            return "registerrrrrrrrr";
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
            return "registerrrrrrrrr";
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
