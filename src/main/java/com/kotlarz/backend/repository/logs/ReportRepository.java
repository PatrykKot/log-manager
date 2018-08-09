package com.kotlarz.backend.repository.logs;

import com.kotlarz.backend.domain.logs.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    List<ReportEntity> findByCustomer_Id(Long id);
}
