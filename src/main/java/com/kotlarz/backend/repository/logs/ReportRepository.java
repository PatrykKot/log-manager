package com.kotlarz.backend.repository.logs;

import com.kotlarz.backend.domain.logs.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Long>, JpaSpecificationExecutor<ReportEntity> {
    List<ReportEntity> findByCustomer_Id(Long id);
}
