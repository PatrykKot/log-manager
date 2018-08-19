package com.kotlarz.backend.service.logs;

import com.kotlarz.backend.domain.logs.CustomerEntity;
import com.kotlarz.backend.domain.logs.CustomerTokenEntity;
import com.kotlarz.backend.domain.logs.ReportEntity;
import com.kotlarz.backend.domain.system.UserType;
import com.kotlarz.backend.repository.logs.CustomerTokenRepository;
import com.kotlarz.backend.repository.logs.ReportRepository;
import com.kotlarz.backend.repository.projection.DashboardReportProjection;
import com.kotlarz.backend.web.dto.NewEventDto;
import com.kotlarz.backend.web.dto.NewReportDto;
import com.kotlarz.configuration.security.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private SecurityService securityService;

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

    private ReportEntity toEntity(NewReportDto reportDto) {
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

    @Transactional
    public Page<DashboardReportProjection> getLatestReportsForUser(Long userId, Pageable pageable) {
        if (securityService.isTypeOf(UserType.ADMIN)) {
            return reportRepository.findForAdmin(pageable);
        } else {
            return reportRepository.findForCustomers(userId, pageable);
        }
    }
}
