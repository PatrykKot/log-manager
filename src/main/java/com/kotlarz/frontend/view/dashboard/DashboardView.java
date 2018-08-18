package com.kotlarz.frontend.view.dashboard;

import com.kotlarz.frontend.dto.DashboardReportDto;
import com.kotlarz.frontend.presenter.dashboard.DashboardPresenter;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;

@SpringView(name = DashboardView.NAME)
@UIScope
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
        reportsGrid.addColumn(DashboardReportDto::getCustomerName)
                .setCaption("Customer");
        reportsGrid.addColumn(DashboardReportDto::getDate)
                .setCaption("Date");
        reportsGrid.addColumn(DashboardReportDto::getLength)
                .setCaption("Length");

        presenter.initView(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        onEnterEvent.accept(event);
    }

    public void setReportsProvider(DataProvider<DashboardReportDto, Void> provider) {
        reportsGrid.setDataProvider(provider);
    }
}
