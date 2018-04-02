package com.kotlarz.frontend.view.configuration.users;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;

@SpringView(name = UsersConfigurationView.NAME)
@UIScope
public class UsersConfigurationView
        extends UsersConfigurationViewDesign implements View {
    public static final String NAME = "configuration/users";

    @PostConstruct
    void init()
    {

    }
}
