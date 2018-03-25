package com.kotlarz.frontend.presenter.customers.reports;

import com.kotlarz.backend.service.ReportService;
import com.kotlarz.frontend.dto.ReportDto;
import com.kotlarz.frontend.presenter.Presenter;
import com.kotlarz.frontend.presenter.customers.reports.events.EventsPresenter;
import com.kotlarz.frontend.util.ParametersUtil;
import com.kotlarz.frontend.util.PathBuilder;
import com.kotlarz.frontend.view.customers.CustomersView;
import com.kotlarz.frontend.view.customers.reports.ReportsView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringNavigator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
@UIScope
public class ReportsPresenter implements Presenter<ReportsView> {
    public static final Integer CUSTOMER_NAME_INDEX = 0;

    public static final Integer CUSTOMER_ID_INDEX = 1;

    @Autowired
    private ReportService reportService;

    @Autowired
    private SpringNavigator navigator;

    @Override
    public void initView(ReportsView view) {
    }

    @Override
    public void handleNavigation(ViewChangeListener.ViewChangeEvent event, ReportsView view) {
        initReports(view, ParametersUtil.resolve(event));
    }

    public void initReports(ReportsView view, List<String> parameters) {
        String customerName = parameters.get(CUSTOMER_NAME_INDEX);
        view.setCustomerName(customerName);

        long customerId = Long.parseLong(parameters.get(CUSTOMER_ID_INDEX));
        List<ReportDto> reports = reportService.getReports(customerId).stream()
                .map(ReportDto::new)
                .collect(Collectors.toList());
        view.setReports(reports);

        view.setOnReportSelected(report -> navigator.navigateTo(PathBuilder.getInstance()
                .view(CustomersView.class)
                .param(customerName)
                .param(customerId)
                .param(EventsPresenter.REPORT_URL_CONST)
                .param(report.getId())
                .build()));
    }
}
