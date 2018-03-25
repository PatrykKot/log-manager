package com.kotlarz.frontend.view.configuration;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class ConfigurationViewDesign extends HorizontalLayout {
    public ConfigurationViewDesign() {
        Design.read(this);
    }
}
