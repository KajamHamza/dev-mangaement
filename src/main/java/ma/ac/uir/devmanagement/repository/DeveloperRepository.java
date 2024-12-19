package ma.ac.uir.devmanagement.repository;

import ma.ac.uir.devmanagement.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    @Query("SELECT d FROM Developer d WHERE d.id IN :ids")
    List<Developer> findAllByCustomIdList(@Param("ids") List<Long> developerIds);

    @Query("SELECT d FROM Developer d JOIN d.skills s WHERE s IN :skills GROUP BY d HAVING COUNT(s) = :skillCount")
    List<Developer> findDevelopersBySkills(@Param("skills") List<String> skills, @Param("skillCount") long skillCount);
}
