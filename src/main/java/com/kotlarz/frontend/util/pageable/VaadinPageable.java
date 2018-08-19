package com.kotlarz.frontend.util.pageable;

import com.kotlarz.backend.repository.logs.projection.DashboardReportProjection;
import com.vaadin.data.provider.Query;

public class VaadinPageable extends OffsetPageable {
    public VaadinPageable(Query<DashboardReportProjection, Void> query) {
        super(query.getOffset(), query.getLimit());
    }
}
