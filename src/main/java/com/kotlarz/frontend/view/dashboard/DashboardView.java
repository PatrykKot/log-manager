package com.kotlarz.frontend.view.dashboard;

import com.kotlarz.backend.repository.projection.DashboardReportProjection;
import com.kotlarz.frontend.presenter.dashboard.DashboardPresenter;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;

@SpringView(name = DashboardView.NAME)
@ViewScope
public class DashboardView
        extends DashboardViewDesign
        implements View {
    public static final String NAME = "dashboard";

    @Autowired
    private DashboardPresenter presenter;

    @Setter
    private Consumer<ViewChangeListener.ViewChangeEvent> onEnterEvent = ($) -> {
    };

    @PostConstruct
    public void init() {
        reportsGrid.addColumn(DashboardReportProjection::getCustomerName)
                .setCaption("Customer");
        reportsGrid.addColumn(DashboardReportProjection::getReportDate)
                .setCaption("Date");
        reportsGrid.addColumn(DashboardReportProjection::getEventsCount)
                .setCaption("Length");

        presenter.initView(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        onEnterEvent.accept(event);
    }

    public void setReportsProvider(DataProvider<DashboardReportProjection, Void> provider) {
        reportsGrid.setDataProvider(provider);
    }

    public void onSelect(Consumer<DashboardReportProjection> handler) {
        reportsGrid.addSelectionListener(event -> event.getFirstSelectedItem()
                .ifPresent(handler));
    }
}
