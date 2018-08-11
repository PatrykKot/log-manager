package com.kotlarz.frontend.view.configuration.users;

import com.kotlarz.frontend.dto.UserDto;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class UsersConfigurationViewDesign
                extends VerticalLayout
{
    protected Grid<UserDto> usersGrid;

    protected Button addUserButton;

    public UsersConfigurationViewDesign()
    {
        Design.read( this );
    }
}
