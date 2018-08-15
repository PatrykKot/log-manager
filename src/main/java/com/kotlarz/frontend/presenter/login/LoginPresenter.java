package com.kotlarz.frontend.presenter.login;

import com.kotlarz.configuration.security.service.SecurityService;
import com.kotlarz.frontend.presenter.Presenter;
import com.kotlarz.frontend.view.dashboard.DashboardView;
import com.kotlarz.frontend.view.login.LoginView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Slf4j
@SpringComponent
@UIScope
public class LoginPresenter
        implements Presenter<LoginView> {

    @Autowired
    private SpringNavigator navigator;

    @Autowired
    private SecurityService securityService;

    @Override
    public void initView(LoginView view) {
        view.setOnLoginClick((username, password) -> {
            try {
                Authentication authentication = securityService.logIn(username, password);

                if (view.isRememberMeSelected()) {
                    securityService.rememberMe(authentication);
                }

                navigator.navigateTo(DashboardView.NAME);
            } catch (AuthenticationException ex) {
                Notification.show("Invalid login data");
            }
        });
    }
}