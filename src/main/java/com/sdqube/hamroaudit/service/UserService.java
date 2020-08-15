package com.sdqube.hamroaudit.service;

import com.sdqube.hamroaudit.endpoint.AuthController;
import com.sdqube.hamroaudit.exception.ResourceNotFoundException;
import com.sdqube.hamroaudit.exception.UserAlreadyExistsException;
import com.sdqube.hamroaudit.messaging.UserActivityEventHandler;
import com.sdqube.hamroaudit.model.Role;
import com.sdqube.hamroaudit.model.User;
import com.sdqube.hamroaudit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 4:14 PM
 */
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(AuthController.class);
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    @Autowired
    private UserActivityEventHandler userActivityEventHandler;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserActivityEventHandler userActivityEventHandler) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userActivityEventHandler = userActivityEventHandler;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findByUsernameIn(List<String> usernames) {
        return userRepository.findByUsernameIn(usernames);
    }

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            String userExistsLog = String.format("Username %s already exists.", user.getUsername());
            log.info(userExistsLog);
            throw new UserAlreadyExistsException(userExistsLog);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            String userExistsLog = String.format("User email %s already exists.", user.getEmail());
            log.info(userExistsLog);
            throw new UserAlreadyExistsException(userExistsLog);
        }
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>() {{
            add(Role.USER);
        }});

        User savedUser = userRepository.save(user);
        userActivityEventHandler.sendUserCreated(savedUser);
        return savedUser;
    }

    public User updateProfilePicture(String uri, String id) {
        log.info("update profile picture {} for user {}", uri, id);

        return userRepository
                .findById(id)
                .map(user -> {
                    String oldProfilePic = user.getUserProfile().getProfilePictureUrl();
                    user.getUserProfile().setProfilePictureUrl(uri);
                    User savedUser = userRepository.save(user);

                    userActivityEventHandler.sendUserUpdated(savedUser, oldProfilePic);

                    return savedUser;
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("user id %s not found", id)));
    }
}
