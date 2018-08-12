package com.kotlarz.frontend.dto;

import com.kotlarz.backend.domain.system.User;
import com.kotlarz.backend.domain.system.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String username;

    private String rawPassword;

    private UserType type;

    public UserDto(User domain) {
        id = domain.getId();
        username = domain.getUsername();
        type = domain.getType();
    }
}
