package com.kotlarz.frontend.view.configuration.customers.single;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class CreateCustomerView
                extends SingleCustomerConfigView
{
    @Override
    @PostConstruct
    void init()
    {
        super.init();
        formButton.setCaption( "Create" );
    }
}
