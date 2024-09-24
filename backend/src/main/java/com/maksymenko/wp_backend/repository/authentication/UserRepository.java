package com.maksymenko.wp_backend.repository.authentication;

import com.maksymenko.wp_backend.model.authentication.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);

}
