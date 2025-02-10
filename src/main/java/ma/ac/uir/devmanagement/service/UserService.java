package ma.ac.uir.devmanagement.service;

import ma.ac.uir.devmanagement.entity.User;

public interface UserService {

    /**
     * Authenticates the user by their username and password.
     *
     * @param username the user's username
     * @param password the user's password
     * @return the authenticated User object, or null if authentication fails
     */
    User authenticate(String username, String password);

}
