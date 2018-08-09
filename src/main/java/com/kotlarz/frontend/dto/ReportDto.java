package com.kotlarz.frontend.dto;

import com.kotlarz.backend.domain.logs.ReportEntity;
import lombok.Data;

import java.util.Date;

@Data
public class ReportDto {
    private Long id;

    private Date date;

    public ReportDto(ReportEntity domain) {
        id = domain.getId();
        date = domain.getDate();
    }
}
