package com.kotlarz.backend.repository.projection;

import java.util.Date;

public interface DashboardReportProjection {
    Long getId();

    Long getCustomerId();

    String getCustomerName();

    Long getEventsCount();

    Date getReportDate();
}
