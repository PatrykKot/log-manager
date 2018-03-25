package com.kotlarz.frontend.view.dashboard;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class DashboardViewDesign extends HorizontalLayout {
    public DashboardViewDesign() {
        Design.read(this);
    }
}
