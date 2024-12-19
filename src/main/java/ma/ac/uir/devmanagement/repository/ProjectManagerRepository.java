package ma.ac.uir.devmanagement.repository;

import ma.ac.uir.devmanagement.entity.ProjectManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectManagerRepository extends JpaRepository<ProjectManager, Long> {

}
