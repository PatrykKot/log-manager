package com.kotlarz.frontend.view.login;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class LoginViewDesign
        extends VerticalLayout {
    protected TextField usernameField;

    protected PasswordField passwordField;

    protected Button loginButton;

    protected CheckBox rememberMeCheckbox;

    public LoginViewDesign() {
        Design.read(this);
    }
}