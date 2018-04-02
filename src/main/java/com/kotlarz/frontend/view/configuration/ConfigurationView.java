package com.kotlarz.frontend.view.configuration;

import com.kotlarz.frontend.presenter.configuration.ConfigurationPresenter;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = ConfigurationView.NAME)
@UIScope
public class ConfigurationView extends ConfigurationViewDesign implements View {
    public static final String NAME = "configuration";

    @Autowired
    private ConfigurationPresenter presenter;


    @PostConstruct
    void init()
    {
        presenter.initView( this );
    }

    public void addOnCustomersClicked( Button.ClickListener listener )
    {
        customersButton.addClickListener( listener );
    }

    public void addOnUsersClicked( Button.ClickListener listener )
    {
        usersButton.addClickListener( listener );
    }

    public void addOnSystemConfigurationClicked( Button.ClickListener listener )
    {
        systemConfigurationButton.addClickListener( listener );
    }
}
