package com.kotlarz.frontend.presenter.configuration.customers.single;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import lombok.Setter;

@SpringComponent
@UIScope
public class SingleCustomerConfigPresenter {
    @Setter
    private Runnable onConfigFinished;
}
