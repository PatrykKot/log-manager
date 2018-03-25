package com.kotlarz.backend.service;

import com.kotlarz.backend.domain.ReportEntity;
import com.kotlarz.backend.repository.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Transactional
    public List<ReportEntity> getReports(Long customerId) {
        return reportRepository.findByCustomer_Id(customerId);
    }

    @Transactional
    public ReportEntity getReport(Long reportId) {
        ReportEntity domain = reportRepository.findOne(reportId);
        if (domain != null)
            Hibernate.initialize(domain.getEvents());
        return domain;
    }
}
