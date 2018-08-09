package com.kotlarz.backend.service.system;

import com.kotlarz.backend.domain.system.User;
import com.kotlarz.backend.domain.system.UserType;
import com.kotlarz.backend.repository.system.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void mockUsers() {
        userRepository.save(User.builder()
                .username("admin")
                .passwordHash(passwordEncoder.encode("admin"))
                .type(UserType.ADMIN)
                .build());

        userRepository.save(User.builder()
                .username("user")
                .passwordHash(passwordEncoder.encode("user"))
                .type(UserType.STANDARD)
                .build());
    }

    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
