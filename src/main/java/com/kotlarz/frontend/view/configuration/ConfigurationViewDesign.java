package com.kotlarz.frontend.view.configuration;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class ConfigurationViewDesign extends VerticalLayout {
    protected Button customersButton;

    protected Button usersButton;

    protected Button systemConfigurationButton;
    public ConfigurationViewDesign() {
        Design.read(this);
    }
}
