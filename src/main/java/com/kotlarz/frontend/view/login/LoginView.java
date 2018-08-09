package com.kotlarz.frontend.view.login;

import com.kotlarz.frontend.presenter.login.LoginPresenter;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.function.BiConsumer;

@SpringView( name = LoginView.NAME )
@UIScope
public class LoginView
                extends LoginViewDesign
                implements View
{
    public static final String NAME = "login";

    @Autowired
    private LoginPresenter presenter;

    @PostConstruct
    public void init()
    {
        presenter.initView( this );
    }

    public void setOnLoginClick( BiConsumer<String, String> handler )
    {
        loginButton.addClickListener( event -> handler.accept( usernameField.getValue(), passwordField.getValue() ) );
    }
}
