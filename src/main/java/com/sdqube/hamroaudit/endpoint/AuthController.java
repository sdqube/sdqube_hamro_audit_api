package com.sdqube.hamroaudit.endpoint;

import com.sdqube.hamroaudit.exception.BadRequestException;
import com.sdqube.hamroaudit.exception.ResourceNotFoundException;
import com.sdqube.hamroaudit.exception.UserAlreadyExistsException;
import com.sdqube.hamroaudit.model.Profile;
import com.sdqube.hamroaudit.model.ProximityUserDetails;
import com.sdqube.hamroaudit.model.User;
import com.sdqube.hamroaudit.payload.ApiResponse;
import com.sdqube.hamroaudit.payload.JwtAuthenticationResponse;
import com.sdqube.hamroaudit.payload.LoginRequest;
import com.sdqube.hamroaudit.payload.SignupRequest;
import com.sdqube.hamroaudit.service.JwtTokenProvider;
import com.sdqube.hamroaudit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 7:31 PM
 */
@RestController
public class AuthController {
    private final Logger log = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("Login User: {}", loginRequest.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);

            User userInfo = (User) authentication.getPrincipal();

            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userInfo));
        } catch (Exception ex) {
            log.error("Error: {}", ex.toString());
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest payload) {
        log.info("creating user {}", payload.getUsername());

        User user=  new User();
        user.setUsername(payload.getUsername());
        user.setEmail(payload.getEmail());
        user.setPassword(payload.getPassword());
        Profile profile = new Profile();
        profile.setDisplayName(payload.getName());
        user.setUserProfile(profile);

        try {
            userService.registerUser(user);
        } catch (UserAlreadyExistsException e) {
            throw new BadRequestException(e.getMessage());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }

    @PutMapping(value = "/users/me/picture", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateProfilePicture(
            @RequestBody String profilePicture,
            @AuthenticationPrincipal ProximityUserDetails userDetails) {

        userService.updateProfilePicture(profilePicture, userDetails.getId());

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Profile picture updated successfully"));
    }

    @GetMapping(value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findUser(@PathVariable("username") String username) {
        log.info("Retrieving user {}", username);

        return userService
                .findByUsername(username)
                .map(user -> ResponseEntity.ok(user))
                .orElseThrow(() -> new ResourceNotFoundException(username));
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        log.info("retrieving all users");

        return ResponseEntity
                .ok(userService.findAll());
    }

}
