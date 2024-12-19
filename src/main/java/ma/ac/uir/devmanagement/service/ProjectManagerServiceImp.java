package ma.ac.uir.devmanagement.service;

import ma.ac.uir.devmanagement.entity.Project;
import ma.ac.uir.devmanagement.entity.Developer;
import ma.ac.uir.devmanagement.entity.Evaluation;
import ma.ac.uir.devmanagement.entity.ProjectManager;
import ma.ac.uir.devmanagement.enums.ProjectStatus;
import ma.ac.uir.devmanagement.repository.ProjectManagerRepository;
import ma.ac.uir.devmanagement.repository.ProjectRepository;
import ma.ac.uir.devmanagement.repository.DeveloperRepository;
import ma.ac.uir.devmanagement.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectManagerServiceImp implements ProjectManagerService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Override
    public List<Project> getProjectsCreatedByManager(Long managerId) {
        return projectRepository.findProjectByProjectManagerId(managerId);
    }

    @Override
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public void createProject(Project project, Long managerId) {
        Optional<ProjectManager> projectManagerOpt = projectManagerRepository.findById(managerId);

        if (projectManagerOpt.isPresent()) {
            ProjectManager projectManager = projectManagerOpt.get();
            project.setProjectManager(projectManager); // Assigning the ProjectManager to the Project

            // Validate that requiredSkills is not empty
            if (project.getRequiredSkills() == null || project.getRequiredSkills().isEmpty()) {
                throw new RuntimeException("Project must have at least one required skill.");
            }

            projectRepository.save(project); // Saving the project with the manager
        } else {
            throw new RuntimeException("Project Manager not found with ID: " + managerId);
        }
    }


    @Override
    public void assignDevelopersToProject(Long projectId, List<Long> developerIds) {
        Project project = getProjectById(projectId);
        List<Developer> developers = developerRepository.findAllByCustomIdList(developerIds);
        project.setDevelopers(developers);
        projectRepository.save(project);
    }

    @Override
    public void updateProjectStatus(Long projectId, String status) {
        // Fetch the project by its ID
        Project project = getProjectById(projectId);

        try {
            // Convert the status string to ProjectStatus enum
            ProjectStatus projectStatus = ProjectStatus.valueOf(status);

            // Set the new status on the project
            project.setStatus(projectStatus);

            // Save the updated project
            projectRepository.save(project);
        } catch (IllegalArgumentException e) {
            // Handle the case where the provided status is invalid
            // Optionally, log the error or set a default status
            throw new IllegalArgumentException("Invalid project status: " + status);
        }
    }


    @Override
    public void evaluateDeveloper(Long projectId, Long developerId, int stars, String feedback) {
        // Fetch the Developer and Project entities by their IDs
        Developer developer = developerRepository.findById(developerId)
                .orElseThrow(() -> new IllegalArgumentException("Developer not found with ID: " + developerId));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));

        // Create the evaluation and set the developer and project
        Evaluation evaluation = new Evaluation();
        evaluation.setDeveloper(developer);  // Set the Developer object
        evaluation.setProject(project);      // Set the Project object
        evaluation.setStars(stars);
        evaluation.setFeedback(feedback);

        // Save the evaluation
        evaluationRepository.save(evaluation);
    }

    @Override
    public ProjectManager getManagerById(Long managerId) {
        Optional<ProjectManager> optionalManager = projectManagerRepository.findById(managerId);
        return optionalManager.orElseThrow(() -> new RuntimeException("Project Manager not found with ID: " + managerId));
    }

    @Override
    public void updateManagerProfile(ProjectManager manager) {
        // Fetch the existing manager from the database
        ProjectManager existingManager = projectManagerRepository.findById(manager.getId())
                .orElseThrow(() -> new RuntimeException("Project Manager not found with ID: " + manager.getId()));


        existingManager.setUsername(manager.getUsername());
        existingManager.setEmail(manager.getEmail());

        // Save updated manager back to the database
        projectManagerRepository.save(existingManager);
    }

}
