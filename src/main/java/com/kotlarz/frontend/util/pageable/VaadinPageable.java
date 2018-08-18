package com.kotlarz.frontend.util.pageable;

import com.kotlarz.frontend.dto.DashboardReportDto;
import com.vaadin.data.provider.Query;

public class VaadinPageable extends OffsetPageable {
    public VaadinPageable(Query<DashboardReportDto, Void> query) {
        super(query.getOffset(), query.getLimit());
    }
}
