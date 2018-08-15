package com.kotlarz.frontend.view.configuration.users.single;

import com.kotlarz.backend.domain.system.UserType;
import com.kotlarz.frontend.dto.AvailableCustomerDto;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class SingleUserConfigViewDesign
        extends Window {
    protected TextField usernameField;

    protected PasswordField passwordField;

    protected Button formButton;

    protected Button deleteButton;

    protected ComboBox<UserType> typeCombo;

    protected Grid<AvailableCustomerDto> availableCustomersGrid;

    protected Button addCustomerButton;

    public SingleUserConfigViewDesign() {
        Design.read(this);
    }
}
