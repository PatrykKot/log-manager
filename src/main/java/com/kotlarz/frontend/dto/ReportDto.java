package com.kotlarz.frontend.dto;

import com.kotlarz.backend.domain.ReportEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Data
public class ReportDto {
    private Long id;

    private Date date;

    public ReportDto(ReportEntity domain) {
        id = domain.getId();
        date = domain.getDate();
    }
}
