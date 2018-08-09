package com.kotlarz.frontend.presenter;

import com.kotlarz.frontend.view.configuration.ConfigurationView;
import com.kotlarz.frontend.view.customers.CustomersView;
import com.kotlarz.frontend.view.dashboard.DashboardView;
import com.kotlarz.frontend.view.main.MainView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringNavigator;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class MainPresenter implements Presenter<MainView> {
    @Autowired
    private SpringNavigator navigator;

    @Override
    public void initView(MainView view) {
        view.getDashboardButton().addClickListener( event -> navigator.navigateTo( DashboardView.NAME ) );
        view.getCustomersButton().addClickListener(event -> navigator.navigateTo(CustomersView.NAME));
        view.getConfigurationButton().addClickListener(event -> navigator.navigateTo(ConfigurationView.NAME));
    }
}
