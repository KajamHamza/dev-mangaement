package ma.ac.uir.devmanagement.service;

import ma.ac.uir.devmanagement.entity.User;
import ma.ac.uir.devmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository that interacts with the database

    /**
     * Authenticates the user by their username and password.
     *
     * @param username the user's username
     * @param password the user's password
     * @return the authenticated User object, or null if authentication fails
     */
    @Override
    public User authenticate(String username, String password) {
        // Fetch the user from the database by username (case insensitive)
        User user = userRepository.findByUsername(username);

        // Check if user exists and if the password matches
        if (user != null && user.getPassword().equals(password)) {
            return user; // Return the authenticated user
        }
        return null; // Return null if authentication fails
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the User object, or null if no user is found
     */
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null); // Return user by ID, or null if not found
    }
}

