package com.kotlarz.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class DashboardReportDto {
    private Long id;

    private String customerName;

    private Long length;

    private Date date;
}
