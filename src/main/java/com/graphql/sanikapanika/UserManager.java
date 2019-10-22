package com.graphql.sanikapanika;

import com.graphql.sanikapanika.exception.UserAlreadyExistsException;
import com.graphql.sanikapanika.exception.UserDoesNotExistException;
import com.graphql.sanikapanika.security.Role;
import com.graphql.sanikapanika.security.RoleRepository;
import com.graphql.sanikapanika.security.User;
import com.graphql.sanikapanika.security.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserManager {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserManager(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User registerUser(String username, String rawPassword) throws UserAlreadyExistsException {

        assertUserNotExists(username);

        User user = new User(username, rawPassword, this.generateUserRoles());
        userRepository.save(user);

        return user;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User confirmUser(String username) throws UserDoesNotExistException {
        assertUserExists(username);

        User user = userRepository.getFirstByUsername(username);
        user.confirmUser();

        return user;
    }

    private void assertUserNotExists(String username) throws UserAlreadyExistsException {
        User user = userRepository.getFirstByUsername(username);
        if (user != null) {
            throw new UserAlreadyExistsException("User with given username already exists");
        }
    }

    private void assertUserExists(String username) throws UserDoesNotExistException {
        User user = userRepository.getFirstByUsername(username);
        if (user == null) {
            throw new UserDoesNotExistException("User with given username does not exist");
        }
    }

    private Collection<Role> generateUserRoles() {
        Collection<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByName("ROLE_USER");
        roles.add(role);

        return roles;
    }
}
