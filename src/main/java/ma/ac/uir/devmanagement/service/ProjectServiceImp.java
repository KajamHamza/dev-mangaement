package ma.ac.uir.devmanagement.service;

import ma.ac.uir.devmanagement.entity.Developer;
import ma.ac.uir.devmanagement.entity.Project;
import ma.ac.uir.devmanagement.entity.ProjectManager;
import ma.ac.uir.devmanagement.enums.ProjectStatus;
import ma.ac.uir.devmanagement.repository.DeveloperRepository;
import ma.ac.uir.devmanagement.repository.ProjectManagerRepository;
import ma.ac.uir.devmanagement.repository.ProjectRepository;
import ma.ac.uir.devmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImp implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Override
    public List<Project> getProjectsAssignedToDeveloper(Long developerId) {
        return projectRepository.findByDevelopersId(developerId);
    }

    @Override
    public Project getProjectDetailsForDeveloper(Long projectId, Long developerId) {
        return projectRepository.findByIdAndDevelopersId(projectId, developerId)
                .orElseThrow(() -> new RuntimeException("Project not found or not assigned to developer"));
    }

    @Override
    public List<Project> getProjectsByStatusForDeveloper(Long developerId, ProjectStatus status) {
        Developer developer = developerRepository.findById(developerId)
                .orElseThrow(() -> new IllegalArgumentException("Developer not found"));
        return developer.getProjects().stream()
                .filter(project -> project.getStatus().toString().equalsIgnoreCase(String.valueOf(status)))
                .collect(Collectors.toList());
    }
}
