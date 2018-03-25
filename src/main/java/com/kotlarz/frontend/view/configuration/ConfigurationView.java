package com.kotlarz.frontend.view.configuration;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;

@SpringView(name = ConfigurationView.NAME)
@UIScope
public class ConfigurationView extends ConfigurationViewDesign implements View {
    public static final String NAME = "configuration";

    @PostConstruct
    void init() {
    }
}
