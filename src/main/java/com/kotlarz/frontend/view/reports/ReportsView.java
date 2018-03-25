package com.kotlarz.frontend.view.reports;

import com.kotlarz.frontend.dto.ReportDto;
import com.kotlarz.frontend.presenter.ReportsPresenter;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@SpringComponent
@UIScope
@Getter
@Setter
public class ReportsView extends ReportsViewDesign implements View {
    @Autowired
    private ReportsPresenter reportsPresenter;

    private Consumer<ReportDto> onReportSelected;

    @PostConstruct
    private void init() {
        customerReportsGrid.addColumn(report -> report.getDate().toString()).setCaption("Date");
        customerReportsGrid.addSelectionListener(selection -> {
            if (onReportSelected != null) {
                Optional<ReportDto> firstSelectedItem = selection.getFirstSelectedItem();
                if (firstSelectedItem.isPresent())
                    onReportSelected.accept(firstSelectedItem.get());
            }
        });

        reportsPresenter.initView(this);
    }

    public void setCustomerName(String name) {
        customerNameLabel.setValue(name);
    }

    public void setReports(List<ReportDto> reports) {
        customerReportsGrid.setItems(reports);
    }
}
