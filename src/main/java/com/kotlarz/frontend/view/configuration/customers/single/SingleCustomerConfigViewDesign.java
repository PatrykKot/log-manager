package com.kotlarz.frontend.view.configuration.customers.single;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Window;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class SingleCustomerConfigViewDesign extends Window {
    public SingleCustomerConfigViewDesign() {
        Design.read(this);
    }
}
