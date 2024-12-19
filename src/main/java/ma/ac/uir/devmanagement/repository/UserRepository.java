package ma.ac.uir.devmanagement.repository;

import ma.ac.uir.devmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    List<User> findByRole(String role);

    User findByUsername(String username);
}