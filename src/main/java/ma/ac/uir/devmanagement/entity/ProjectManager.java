package ma.ac.uir.devmanagement.entity;

import jakarta.persistence.*;
import ma.ac.uir.devmanagement.enums.Role;

import java.util.List;

@Entity
public class ProjectManager extends User {

    @OneToMany(mappedBy = "projectManager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Project> projects;

    // Constructors
    public ProjectManager() {
    }

    public ProjectManager(String username, String password, String email, Role role) {
        super(username, password, email, role);
    }

    // Getters and Setters
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
