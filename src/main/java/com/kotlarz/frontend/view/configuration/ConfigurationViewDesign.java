package com.kotlarz.frontend.view.configuration;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class ConfigurationViewDesign extends VerticalLayout {
    public ConfigurationViewDesign() {
        Design.read(this);
    }
}
