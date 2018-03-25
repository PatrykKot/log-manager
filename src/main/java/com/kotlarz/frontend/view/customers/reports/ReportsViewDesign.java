package com.kotlarz.frontend.view.customers.reports;

import com.kotlarz.frontend.dto.ReportDto;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class ReportsViewDesign extends VerticalLayout {
    protected Label customerNameLabel;
    protected Grid<ReportDto> customerReportsGrid;

    public ReportsViewDesign() {
        Design.read(this);
    }
}
