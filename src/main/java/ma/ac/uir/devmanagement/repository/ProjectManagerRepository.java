package ma.ac.uir.devmanagement.repository;

import ma.ac.uir.devmanagement.entity.ProjectManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectManagerRepository extends JpaRepository<ProjectManager, Integer> {
}
