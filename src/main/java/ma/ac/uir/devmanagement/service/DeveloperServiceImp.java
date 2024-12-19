package ma.ac.uir.devmanagement.service;

import ma.ac.uir.devmanagement.entity.Developer;
import ma.ac.uir.devmanagement.entity.Evaluation;
import ma.ac.uir.devmanagement.repository.DeveloperRepository;
import ma.ac.uir.devmanagement.repository.EvaluationRepository;
import ma.ac.uir.devmanagement.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeveloperServiceImp implements DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Override
    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    @Override
    public Developer getDeveloperById(Long id) {
        return developerRepository.findById(id).orElseThrow(() -> new RuntimeException("Developer not found"));
    }

    @Override
    public void saveDeveloper(Developer developer) {
        developerRepository.save(developer);
    }

    @Override
    public void updateSkills(Long developerId, List<String> skills) {
        Developer developer = getDeveloperById(developerId);
        developer.setSkills(skills);
        developerRepository.save(developer);
    }

    @Override
    public Evaluation getEvaluationForProject(Long developerId, Long projectId) {
        return evaluationRepository.findByDeveloperIdAndProjectId(developerId, projectId);
    }

    @Override
    public List<Developer> findDevelopersBySkills(List<String> skills) {
        return developerRepository.findDevelopersBySkills(skills, skills.size());
    }

    @Override
    public List<String> getAllSkills() {
        return developerRepository.findAll()
                .stream()
                .flatMap(developer -> developer.getSkills().stream()) // Combine all skill lists
                .distinct() // Remove duplicates
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalRatings(Long developerId) {
        return evaluationRepository.countByDeveloperId(developerId);
    }

    @Override
    public void updateDeveloperProfile(Long developerId, String username, String email, List<String> skills) {
        // Retrieve the developer from the database
        Developer developer = developerRepository.findById(developerId)
                .orElseThrow(() -> new IllegalArgumentException("Developer not found"));

        developer.setUsername(username);
        developer.setEmail(email);
        developer.setSkills(skills);

        // Save the updated developer back to the database
        developerRepository.save(developer);
    }


}

