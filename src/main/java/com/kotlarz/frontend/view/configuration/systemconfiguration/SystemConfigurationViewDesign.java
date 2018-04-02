package com.kotlarz.frontend.view.configuration.systemconfiguration;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class SystemConfigurationViewDesign
                extends VerticalLayout
{
    public SystemConfigurationViewDesign()
    {
        Design.read( this );
    }
}
