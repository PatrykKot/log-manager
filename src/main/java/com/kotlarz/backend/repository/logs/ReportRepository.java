package com.kotlarz.backend.repository.logs;

import com.kotlarz.backend.domain.logs.ReportEntity;
import com.kotlarz.backend.repository.projection.DashboardReportProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Long>, JpaSpecificationExecutor<ReportEntity> {
    List<ReportEntity> findByCustomer_Id(Long id);

    @Query("select report.id as id," +
            "report.customer.name as customerName," +
            "report.customer.id as customerId," +
            "report.date as reportDate," +
            "count(elements(report.events)) as eventsCount " +
            "from ReportEntity report where report.customer.id in :customerIds " +
            "group by report.id, report.customer.id, report.customer.name, report.date")
    Page<DashboardReportProjection> findByCustomers(@Param("customerIds") Collection<Long> customerIds, Pageable pageable);

    @Query("select count(*) from ReportEntity report where report.customer.id in :customerIds")
    Long countByCustomers(@Param("customerIds") Collection<Long> customerIds);
}
