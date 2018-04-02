package com.kotlarz.backend.web.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewReportDto {
    private String token;

    private Date reported;

    private List<NewEventDto> events;
}
