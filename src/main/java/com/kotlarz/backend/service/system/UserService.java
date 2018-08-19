package com.kotlarz.backend.service.system;

import com.kotlarz.backend.domain.system.UserEntity;
import com.kotlarz.backend.domain.system.UserType;
import com.kotlarz.backend.repository.system.UserRepository;
import com.kotlarz.backend.service.logs.CustomerService;
import com.kotlarz.backend.service.system.exception.UserAlreadyExistException;
import com.kotlarz.configuration.security.exception.UserNotFoundException;
import com.kotlarz.configuration.security.service.SecurityService;
import com.kotlarz.frontend.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CustomerService customerService;

    @PostConstruct
    @Transactional
    public void mockUsers() {
        if (userRepository.count() == 0) {
            userRepository.save(UserEntity.builder()
                    .username("admin")
                    .passwordHash(passwordEncoder.encode("admin"))
                    .type(UserType.ADMIN)
                    .build());

            userRepository.save(UserEntity.builder()
                    .username("user")
                    .passwordHash(passwordEncoder.encode("user"))
                    .type(UserType.STANDARD)
                    .build());
        }
    }

    @Transactional
    public Optional<UserEntity> getUser(Long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Transactional
    public Optional<UserEntity> getUserWithCustomers(Long id) {
        UserEntity user = userRepository.findOne(id);
        if (user != null) {
            Hibernate.initialize(user.getAvailableCustomers());
        }

        return Optional.ofNullable(user);
    }

    @Transactional
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void create(UserDto userDto) throws UserAlreadyExistException {
        if (findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistException();
        }

        UserEntity user = UserEntity.builder()
                .username(userDto.getUsername())
                .type(userDto.getType())
                .passwordHash(passwordEncoder.encode(userDto.getRawPassword()))
                .availableCustomers(userDto.getAvailableCustomers().stream()
                        .map(dto -> customerService.getCustomer(dto.getId())
                                .orElseThrow(RuntimeException::new))
                        .collect(Collectors.toList()))
                .build();

        userRepository.save(user);
    }

    @Transactional
    public void update(UserDto dto) throws UserAlreadyExistException {
        UserEntity toUpdate = getUser(dto.getId()).orElseThrow(UserNotFoundException::new);
        toUpdate.setType(dto.getType());

        if (!toUpdate.getUsername().equals(dto.getUsername())) {
            if (findByUsername(dto.getUsername()).isPresent()) {
                throw new UserAlreadyExistException();
            }

            toUpdate.setUsername(dto.getUsername());
        }

        toUpdate.getAvailableCustomers().clear();
        toUpdate.setAvailableCustomers(dto.getAvailableCustomers().stream()
                .map(customerDto -> customerService.getCustomer(customerDto.getId())
                        .orElseThrow(RuntimeException::new))
                .collect(Collectors.toList()));

        if (StringUtils.isNotEmpty(dto.getRawPassword())) {
            toUpdate.setPasswordHash(passwordEncoder.encode(dto.getRawPassword()));
        }

        userRepository.save(toUpdate);
    }

    @Transactional
    public void delete(Long userId) {
        userRepository.delete(userId);
        securityService.logOut(userId);
    }
}
