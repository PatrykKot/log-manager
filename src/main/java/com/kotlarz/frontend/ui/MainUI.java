package com.kotlarz.frontend.ui;

import com.kotlarz.configuration.Application;
import com.kotlarz.configuration.security.service.SecurityService;
import com.kotlarz.frontend.view.dashboard.DashboardView;
import com.kotlarz.frontend.view.login.LoginView;
import com.kotlarz.frontend.view.main.MainView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.UI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

@Theme("apptheme")
@Title("Crashed")
@PushStateNavigation
@SpringUI(path = MainUI.UI_PATH)
public class MainUI
        extends UI {
    public static final String UI_PATH = Application.APP_URL;

    public static final String LOGIN_REDIRECT_ATTRIBUTE = "loginRedirect";

    @Autowired
    private MainView mainViewDisplay;

    @Autowired
    private SpringNavigator navigator;

    @Autowired
    private SecurityService securityService;

    @Override
    protected void init(VaadinRequest request) {
        if (!securityService.isLoggedIn()) {
            securityService.tryAutoLogin();
        }

        setContent(mainViewDisplay);

        HttpServletRequest httpServletRequest = ((VaadinServletRequest) request).getHttpServletRequest();
        String requestURI = httpServletRequest.getRequestURI();

        if (securityService.isLoggedIn()) {
            if (requestURI.replace("/", StringUtils.EMPTY).equals(UI_PATH)) {
                navigator.navigateTo(DashboardView.NAME);
            }
        } else {
            String path = requestURI.replace("/" + UI_PATH + "/", StringUtils.EMPTY);
            VaadinSession.getCurrent().setAttribute(LOGIN_REDIRECT_ATTRIBUTE, path);
            navigator.navigateTo(LoginView.NAME);
        }
    }
}
