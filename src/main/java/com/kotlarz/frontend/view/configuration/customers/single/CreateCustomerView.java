package com.kotlarz.frontend.view.configuration.customers.single;

import com.kotlarz.frontend.presenter.configuration.customers.single.SingleCustomerConfigPresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.AbstractOrderedLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class CreateCustomerView
                extends SingleCustomerConfigView
{
    @Autowired
    private SingleCustomerConfigPresenter presenter;

    @Override
    @PostConstruct
    void init()
    {
        super.init();
        formButton.setCaption( "Create" );
        AbstractOrderedLayout parent = (AbstractOrderedLayout) deleteButton.getParent();
        parent.removeComponent(deleteButton);

        presenter.init(this);
    }
}
