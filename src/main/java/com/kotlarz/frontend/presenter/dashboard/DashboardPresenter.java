package com.kotlarz.frontend.presenter.dashboard;

import com.kotlarz.backend.service.logs.ReportService;
import com.kotlarz.configuration.security.service.SecurityService;
import com.kotlarz.frontend.dto.DashboardReportDto;
import com.kotlarz.frontend.presenter.Presenter;
import com.kotlarz.frontend.util.pageable.VaadinPageable;
import com.kotlarz.frontend.view.dashboard.DashboardView;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
@Slf4j
public class DashboardPresenter implements Presenter<DashboardView> {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private ReportService reportService;

    @Override
    public void initView(DashboardView view) {
        view.setReportsProvider(buildDataProvider());
    }

    private DataProvider<DashboardReportDto, Void> buildDataProvider() {
        return DataProvider.fromCallbacks(query -> reportService.getLatestReportsForUserDto(securityService.getCurrentUserId(), new VaadinPageable(query)),
                query -> reportService.getLatestReportsForUserCount(securityService.getCurrentUserId()).intValue());
    }
}
