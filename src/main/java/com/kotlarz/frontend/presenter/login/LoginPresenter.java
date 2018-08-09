package com.kotlarz.frontend.presenter.login;

import com.kotlarz.frontend.presenter.Presenter;
import com.kotlarz.frontend.view.login.LoginView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class LoginPresenter
                implements Presenter<LoginView>
{
    @Override
    public void initView( LoginView view )
    {
        view.setOnLoginClick( ( login, password ) -> {
            // TODO manual security login
        } );
    }
}