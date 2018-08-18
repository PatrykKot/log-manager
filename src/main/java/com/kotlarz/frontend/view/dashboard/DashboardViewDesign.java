package com.kotlarz.frontend.view.dashboard;

import com.kotlarz.frontend.dto.DashboardReportDto;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class DashboardViewDesign extends VerticalLayout {
    protected Grid<DashboardReportDto> reportsGrid;

    public DashboardViewDesign() {
        Design.read(this);
    }
}
