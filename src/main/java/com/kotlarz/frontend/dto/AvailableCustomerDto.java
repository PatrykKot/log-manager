package com.kotlarz.frontend.dto;

import com.kotlarz.backend.domain.logs.CustomerEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvailableCustomerDto {
    private Long id;

    private String name;

    public AvailableCustomerDto(CustomerEntity domain) {
        id = domain.getId();
        name = domain.getName();
    }
}
