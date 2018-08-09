package com.kotlarz.frontend.view.login;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class LoginViewDesign
                extends VerticalLayout
{
    protected TextField usernameField;

    protected PasswordField passwordField;

    protected Button loginButton;

    public LoginViewDesign()
    {
        Design.read( this );
    }
}