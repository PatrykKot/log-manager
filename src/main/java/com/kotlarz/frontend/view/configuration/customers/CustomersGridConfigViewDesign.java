package com.kotlarz.frontend.view.configuration.customers;

import com.kotlarz.frontend.dto.CustomerDto;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class CustomersGridConfigViewDesign extends VerticalLayout {
    protected Grid<CustomerDto> customersGrid;

    public CustomersGridConfigViewDesign() {
        Design.read(this);
    }
}
