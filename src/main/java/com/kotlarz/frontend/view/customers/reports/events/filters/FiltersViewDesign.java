package com.kotlarz.frontend.view.customers.reports.events.filters;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class FiltersViewDesign extends Window {
    protected ComboBox<String> threadCombo;
    protected ComboBox<String> userCombo;
    protected ComboBox<String> levelCombo;
    protected ComboBox<String> classCombo;

    public FiltersViewDesign() {
        Design.read(this);
    }
}
