package ma.ac.uir.devmanagement.service;

import ma.ac.uir.devmanagement.entity.Project;
import ma.ac.uir.devmanagement.entity.Developer;
import ma.ac.uir.devmanagement.entity.ProjectManager;

import java.util.List;

public interface ProjectManagerService {
    List<Project> getProjectsCreatedByManager(Long managerId);
    Project getProjectById(Long projectId);
    void createProject(Project project, Long managerId);
    void assignDevelopersToProject(Long projectId, List<Long> developerIds);
    void updateProjectStatus(Long projectId, String status);
    void evaluateDeveloper(Long projectId, Long developerId, int stars, String feedback);


    ProjectManager getManagerById(Long managerId);

    void updateManagerProfile(ProjectManager manager);
}
