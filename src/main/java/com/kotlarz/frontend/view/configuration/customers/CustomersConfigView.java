package com.kotlarz.frontend.view.configuration.customers;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;

@SpringView( name = CustomersConfigView.NAME )
@UIScope
public class CustomersConfigView
                extends CustomersConfigViewDesign
                implements View
{
    public static final String NAME = "configuration/customers";

    @PostConstruct
    void init()
    {

    }
}
