package ma.ac.uir.devmanagement.service;

import ma.ac.uir.devmanagement.entity.Project;
import ma.ac.uir.devmanagement.enums.ProjectStatus;

import java.util.List;

public interface ProjectService {
    List<Project> getProjectsAssignedToDeveloper(Long developerId);
    Project getProjectDetailsForDeveloper(Long projectId, Long developerId);

    List<Project> getProjectsByStatusForDeveloper(Long developerId, ProjectStatus status);
}
