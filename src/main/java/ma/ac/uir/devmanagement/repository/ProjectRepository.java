package ma.ac.uir.devmanagement.repository;

import ma.ac.uir.devmanagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByDevelopersId(Long developerId);

    Optional<Project> findByIdAndDevelopersId(Long projectId, Long developerId);

    List<Project> findProjectByProjectManagerId(Long managerId);
}
