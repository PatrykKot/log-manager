package com.kotlarz.frontend.dto;

import com.kotlarz.backend.domain.ReportEntity;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ReportWithEventsDto extends ReportDto {
    private List<EventDto> events;

    public ReportWithEventsDto(ReportEntity domain) {
        super(domain);
        events = domain.getEvents().stream()
                .map(event -> new EventDto(event))
                .collect(Collectors.toList());
    }
}
