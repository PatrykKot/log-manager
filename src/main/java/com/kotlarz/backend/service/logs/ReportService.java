package com.kotlarz.backend.service.logs;

import com.kotlarz.backend.domain.logs.CustomerEntity;
import com.kotlarz.backend.domain.logs.CustomerTokenEntity;
import com.kotlarz.backend.domain.logs.ReportEntity;
import com.kotlarz.backend.repository.logs.CustomerTokenRepository;
import com.kotlarz.backend.repository.logs.ReportRepository;
import com.kotlarz.backend.web.dto.NewEventDto;
import com.kotlarz.backend.web.dto.NewReportDto;
import com.kotlarz.frontend.dto.DashboardReportDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CustomerTokenRepository tokenRepository;

    @Autowired
    private CustomerService customerService;

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
    public Stream<DashboardReportDto> getLatestReportsForUserDto(Long userId, Pageable pageable) {
        return getLatestReportsForUser(userId, pageable)
                .stream()
                .map(report -> DashboardReportDto.builder()
                        .customerName(report.getCustomer().getName())
                        .date(report.getDate())
                        .length((long) report.getEvents().size())
                        .build())
                .collect(Collectors.toList())
                .stream();
    }

    @Transactional
    public List<ReportEntity> getLatestReportsForUser(Long userId, Pageable pageable) {
        List<CustomerEntity> customers = customerService.getCustomersForUser(userId);
        Page<ReportEntity> page = reportRepository.findAll(reportsInCustomersSpecification(customers), pageable);
        return page.getContent();
    }

    @Transactional
    public Long getLatestReportsForUserCount(Long userId) {
        List<CustomerEntity> customers = customerService.getCustomersForUser(userId);
        return reportRepository.count(reportsInCustomersSpecification(customers));
    }

    private Specification<ReportEntity> reportsInCustomersSpecification(List<CustomerEntity> customers) {
        return (root, query, cb) -> cb.isTrue(root.get("customer").in(customers));
    }
}
