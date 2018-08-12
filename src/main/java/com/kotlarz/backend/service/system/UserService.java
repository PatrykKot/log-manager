package com.kotlarz.backend.service.system;

import com.kotlarz.backend.domain.system.User;
import com.kotlarz.backend.domain.system.UserType;
import com.kotlarz.backend.repository.system.UserRepository;
import com.kotlarz.frontend.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
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
    public Optional<User> getUser(Long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void create(UserDto userDto) {
        // TODO check the same username
        User user = User.builder()
                .username(userDto.getUsername())
                .type(userDto.getType())
                .passwordHash(passwordEncoder.encode(userDto.getRawPassword()))
                .build();

        userRepository.save(user);
    }

    @Transactional
    public void update(UserDto dto) {
        User toUpdate = getUser(dto.getId()).orElseThrow(RuntimeException::new);
        toUpdate.setUsername(dto.getUsername());
        toUpdate.setType(dto.getType());

        if (StringUtils.isNotEmpty(dto.getRawPassword())) {
            toUpdate.setPasswordHash(passwordEncoder.encode(dto.getRawPassword()));
        }

        userRepository.save(toUpdate);
    }

    @Transactional
    public void delete(Long userId) {
        userRepository.delete(userId);
    }
}
