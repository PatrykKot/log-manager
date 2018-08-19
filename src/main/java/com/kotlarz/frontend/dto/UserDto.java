package com.kotlarz.frontend.dto;

import com.kotlarz.backend.domain.system.UserEntity;
import com.kotlarz.backend.domain.system.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String username;

    private String rawPassword;

    private UserType type;

    private List<AvailableCustomerDto> availableCustomers;

    public UserDto(UserEntity domain, Boolean mapCustomers
    ) {
        id = domain.getId();
        username = domain.getUsername();
        type = domain.getType();

        if (mapCustomers) {
            availableCustomers = domain.getAvailableCustomers().stream()
                    .map(AvailableCustomerDto::new)
                    .collect(Collectors.toList());
        }
    }
}
