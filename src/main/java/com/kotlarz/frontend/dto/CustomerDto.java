package com.kotlarz.frontend.dto;

import com.kotlarz.backend.domain.logs.CustomerEntity;
import com.kotlarz.backend.domain.logs.FormatterConfigEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

    public CustomerEntity toEntity() {
        return CustomerEntity.builder()
                .id(id)
                .name(name)
                .clearLogsAfterDays(clearLogsAfterDays)
                .formatter(FormatterConfigEntity.builder()
                        .fill(fillPattern)
                        .pattern(pattern)
                        .build())
                .build();
    }
}
