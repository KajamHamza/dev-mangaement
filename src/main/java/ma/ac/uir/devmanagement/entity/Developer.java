package ma.ac.uir.devmanagement.entity;

import jakarta.persistence.*;
import ma.ac.uir.devmanagement.enums.Role;

import java.util.List;

@Entity
public class Developer extends User {

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "developer_skills", joinColumns = @JoinColumn(name = "developer_id"))
    @Column(name = "skill")
    private List<String> skills;

    @ManyToMany(mappedBy = "developers", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Project> projects;

    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Evaluation> evaluations;

    // Constructors
    public Developer() {
    }

    public Developer(String username, String password, String email, Role role) {
        super(username, password, email, role);
    }

    // Getters and Setters
    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
}

