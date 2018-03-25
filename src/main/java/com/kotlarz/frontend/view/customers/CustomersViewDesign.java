package com.kotlarz.frontend.view.customers;

import com.kotlarz.frontend.dto.CustomerDto;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class CustomersViewDesign extends VerticalLayout {
    protected Grid<CustomerDto> customersGrid;

    public CustomersViewDesign() {
        Design.read(this);
    }
}
