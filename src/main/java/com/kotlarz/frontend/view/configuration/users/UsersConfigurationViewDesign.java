package com.kotlarz.frontend.view.configuration.users;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class UsersConfigurationViewDesign
                extends VerticalLayout
{
    public UsersConfigurationViewDesign()
    {
        Design.read( this );
    }
}
