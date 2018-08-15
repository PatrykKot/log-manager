package com.kotlarz.frontend.presenter.login;

import com.kotlarz.configuration.security.service.SecurityService;
import com.kotlarz.frontend.presenter.Presenter;
import com.kotlarz.frontend.ui.MainUI;
import com.kotlarz.frontend.view.dashboard.DashboardView;
import com.kotlarz.frontend.view.login.LoginView;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Notification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private SpringViewProvider viewProvider;

    @Override
    public void initView(LoginView view) {
        view.setOnLoginClick((username, password) -> {
            try {
                Authentication authentication = securityService.logIn(username, password);

                if (view.isRememberMeSelected()) {
                    securityService.rememberMe(authentication);
                }

                String loginRedirect = (String) VaadinSession.getCurrent().getAttribute(MainUI.LOGIN_REDIRECT_ATTRIBUTE);
                if (StringUtils.isNotEmpty(loginRedirect)) {
                    VaadinSession.getCurrent().setAttribute(MainUI.LOGIN_REDIRECT_ATTRIBUTE, null);

                    String viewName = viewProvider.getViewName(loginRedirect);
                    if (StringUtils.isNotEmpty(viewProvider.getViewName(loginRedirect))) {
                        navigator.navigateTo(viewName);
                    } else {
                        navigator.navigateTo(DashboardView.NAME);
                    }
                } else {
                    navigator.navigateTo(DashboardView.NAME);
                }
            } catch (AuthenticationException ex) {
                Notification.show("Invalid login data");
            }
        });
    }
}