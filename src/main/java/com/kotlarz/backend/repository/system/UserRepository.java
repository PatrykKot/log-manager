package com.kotlarz.backend.repository.system;

import com.kotlarz.backend.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
