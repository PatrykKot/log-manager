package com.kotlarz.frontend.presenter.configuration;

import com.kotlarz.frontend.presenter.Presenter;
import com.kotlarz.frontend.util.PathBuilder;
import com.kotlarz.frontend.view.configuration.ConfigurationView;
import com.kotlarz.frontend.view.configuration.customers.CustomersGridConfigView;
import com.kotlarz.frontend.view.configuration.users.UsersConfigurationView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.spring.navigator.SpringNavigator;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@ViewScope
public class ConfigurationPresenter
        implements Presenter<ConfigurationView> {
    @Autowired
    private SpringNavigator navigator;

    @Override
    public void initView(ConfigurationView view) {
        view.addOnCustomersClicked(event -> navigator.navigateTo(PathBuilder.getInstance()
                .view(CustomersGridConfigView.class)
                .build()));

        view.addOnUsersClicked(event -> navigator.navigateTo(PathBuilder.getInstance()
                .view(UsersConfigurationView.class)
                .build()));
    }
}
