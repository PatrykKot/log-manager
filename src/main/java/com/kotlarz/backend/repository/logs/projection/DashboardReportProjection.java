package com.kotlarz.backend.repository.logs.projection;

import java.util.Date;

public interface DashboardReportProjection {
    Long getId();

    Long getCustomerId();

    String getCustomerName();

    Long getEventsCount();

    Date getReportDate();
}
