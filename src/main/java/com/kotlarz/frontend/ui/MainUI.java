package com.kotlarz.frontend.ui;

import com.kotlarz.frontend.view.dashboard.Dashboard;
import com.kotlarz.frontend.view.main.MainView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

@SpringUI
@Theme("apptheme")
@Title( "Crashed" )
@PushStateNavigation
public class MainUI
        extends UI {

    @Autowired
    private MainView mainViewDisplay;

    @Autowired
    private SpringNavigator navigator;

    @Override
    protected void init(VaadinRequest request) {
        setContent(mainViewDisplay);

        HttpServletRequest httpServletRequest = ((VaadinServletRequest) request).getHttpServletRequest();
        if (httpServletRequest.getRequestURI().equals("/")) {
            navigator.navigateTo(Dashboard.NAME);
        }
    }
}
