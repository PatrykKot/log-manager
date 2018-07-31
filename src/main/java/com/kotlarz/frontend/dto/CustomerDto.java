package com.kotlarz.frontend.dto;

import com.kotlarz.backend.domain.CustomerEntity;
import lombok.Data;

@Data
public class CustomerDto {
    private Long id;

    private String name;

    private String pattern;

    private Long clearLogsAfterDays;

    private Boolean fillPattern;

    public CustomerDto(CustomerEntity domain) {
        name = domain.getName();
        id = domain.getId();
        pattern = domain.getFormatter().getPattern();
        clearLogsAfterDays = domain.getClearLogsAfterDays();
        fillPattern = domain.getFormatter().getFill();
    }
}
