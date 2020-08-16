package com.sdqube.hamroaudit.service;

import com.sdqube.hamroaudit.endpoint.AuthController;
import com.sdqube.hamroaudit.model.ProximityUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 4:13 PM
 */
@Service
public class ProximityUserDetailsService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(ProximityUserDetailsService.class);

    private UserService userService;

    public ProximityUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByUsername(username)
                .map(ProximityUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}