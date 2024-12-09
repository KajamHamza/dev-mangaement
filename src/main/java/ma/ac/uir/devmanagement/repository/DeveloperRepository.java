package ma.ac.uir.devmanagement.repository;


import ma.ac.uir.devmanagement.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}
