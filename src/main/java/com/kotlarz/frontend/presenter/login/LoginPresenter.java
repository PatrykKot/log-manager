package com.kotlarz.frontend.presenter.login;

import com.kotlarz.frontend.presenter.Presenter;
import com.kotlarz.frontend.view.dashboard.DashboardView;
import com.kotlarz.frontend.view.login.LoginView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@SpringComponent
@UIScope
public class LoginPresenter
        implements Presenter<LoginView> {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SpringNavigator navigator;

    @Override
    public void initView(LoginView view) {
        view.setOnLoginClick((username, password) -> {
            try {
                Authentication token = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(username, password));
                SecurityContextHolder.getContext().setAuthentication(token);
                navigator.navigateTo(DashboardView.NAME);
            } catch (AuthenticationException ex) {
                Notification.show("Invalid login data");
            }
        });
    }
}