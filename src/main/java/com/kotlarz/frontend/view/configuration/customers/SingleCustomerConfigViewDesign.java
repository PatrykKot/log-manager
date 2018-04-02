package com.kotlarz.frontend.view.configuration.customers;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class SingleCustomerConfigViewDesign extends VerticalLayout {
    public SingleCustomerConfigViewDesign() {
        Design.read(this);
    }
}
