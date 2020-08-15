package com.sdqube.hamroaudit.repository;

import com.sdqube.hamroaudit.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 4:15 PM
 */
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    List<User> findByUsernameIn(List<String> usernames);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
