package com.kotlarz.frontend.presenter.dashboard;

import com.kotlarz.backend.repository.logs.projection.DashboardReportProjection;
import com.kotlarz.backend.service.logs.ReportService;
import com.kotlarz.configuration.security.service.SecurityService;
import com.kotlarz.frontend.presenter.Presenter;
import com.kotlarz.frontend.presenter.customers.reports.events.EventsPresenter;
import com.kotlarz.frontend.util.PathBuilder;
import com.kotlarz.frontend.util.pageable.VaadinPageable;
import com.kotlarz.frontend.view.customers.CustomersView;
import com.kotlarz.frontend.view.dashboard.DashboardView;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.spring.navigator.SpringNavigator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

@SpringComponent
@ViewScope
@Slf4j
public class DashboardPresenter implements Presenter<DashboardView> {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private SpringNavigator navigator;

    @Override
    public void initView(DashboardView view) {
        view.setReportsProvider(buildDataProvider());
        view.onSelect(report -> navigator.navigateTo(PathBuilder.getInstance()
                .view(CustomersView.class)
                .param(report.getCustomerName())
                .param(report.getCustomerId())
                .param(EventsPresenter.REPORT_URL_CONST)
                .param(report.getId())
                .build()));
    }

    private DataProvider<DashboardReportProjection, Void> buildDataProvider() {
        return DataProvider.fromCallbacks(
                query -> getPage(query).getContent().stream(),
                query -> getPage(query).getContent().size());
    }

    private Page<DashboardReportProjection> getPage(Query<DashboardReportProjection, Void> query) {
        return reportService.getLatestReportsForUser(securityService.getCurrentUserId(),
                new VaadinPageable(query));
    }
}
