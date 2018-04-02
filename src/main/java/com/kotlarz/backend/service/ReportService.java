package com.kotlarz.backend.service;

import com.kotlarz.backend.domain.CustomerEntity;
import com.kotlarz.backend.domain.CustomerTokenEntity;
import com.kotlarz.backend.domain.ReportEntity;
import com.kotlarz.backend.repository.CustomerTokenRepository;
import com.kotlarz.backend.repository.ReportRepository;
import com.kotlarz.backend.web.dto.NewEventDto;
import com.kotlarz.backend.web.dto.NewReportDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CustomerTokenRepository tokenRepository;

    @Transactional
    public List<ReportEntity> getReports(Long customerId) {
        return reportRepository.findByCustomer_Id(customerId);
    }

    @Transactional
    public Optional<ReportEntity> getReport(Long reportId) {
        ReportEntity domain = reportRepository.findOne(reportId);
        if (domain != null)
            Hibernate.initialize(domain.getEvents());
        return Optional.ofNullable(domain);
    }

    @Transactional
    public void create(NewReportDto newReportDto) {
        validate(newReportDto);
        reportRepository.save(toEntity(newReportDto));
    }

    private void validate(NewReportDto newReportDto) {
        Assert.hasText(newReportDto.getToken(), "Token cannot be null");
        Assert.notEmpty(newReportDto.getEvents(), "Events list cannot be empty");
        Assert.notNull(newReportDto.getReported(), "Report date cannot be null");
    }

    @Transactional
    protected ReportEntity toEntity(NewReportDto reportDto) {
        CustomerTokenEntity customerToken = tokenRepository.findByToken(reportDto.getToken());
        if (customerToken == null) {
            throw new IllegalArgumentException("Cannot find customer with token " + reportDto.getToken());
        }

        CustomerEntity customer = customerToken.getCustomer();
        return ReportEntity.builder()
                .date(reportDto.getReported())
                .customer(customer)
                .events(reportDto.getEvents().stream()
                        .map(NewEventDto::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
