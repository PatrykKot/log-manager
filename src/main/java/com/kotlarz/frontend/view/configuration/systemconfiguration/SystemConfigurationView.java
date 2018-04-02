package com.kotlarz.frontend.view.configuration.systemconfiguration;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;

@SpringView(name = SystemConfigurationView.NAME)
@UIScope
public class SystemConfigurationView
        extends SystemConfigurationViewDesign implements View {
    public static final String NAME = "configuration/system";

    @PostConstruct
    void init()
    {

    }
}
