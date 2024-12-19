package ma.ac.uir.devmanagement.service;

import ma.ac.uir.devmanagement.entity.Developer;
import ma.ac.uir.devmanagement.entity.Evaluation;

import java.util.List;

public interface DeveloperService {
    List<Developer> getAllDevelopers();
    Developer getDeveloperById(Long id);
    void saveDeveloper(Developer developer);
    void updateSkills(Long developerId, List<String> skills);
    Evaluation getEvaluationForProject(Long developerId, Long projectId);
    List<Developer> findDevelopersBySkills(List<String> skills);

    Object getAllSkills();

    int getTotalRatings(Long developerId);

    void updateDeveloperProfile(Long developerId, String username, String email, List<String> skills);
}
