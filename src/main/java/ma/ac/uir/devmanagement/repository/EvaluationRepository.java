package ma.ac.uir.devmanagement.repository;

import ma.ac.uir.devmanagement.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    Evaluation findByDeveloperIdAndProjectId(Long developerId, Long projectId);

    int countByDeveloperId(Long developerId);
}
