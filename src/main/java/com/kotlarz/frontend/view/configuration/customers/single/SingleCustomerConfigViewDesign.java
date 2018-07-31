package com.kotlarz.frontend.view.configuration.customers.single;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class SingleCustomerConfigViewDesign
                extends Window
{
    protected TextField customerNameField;

    protected TextField clearLogsAfterDaysField;

    protected TextField formatterPatternField;

    protected CheckBox fillLogsCheckBox;

    protected Button formButton;

    public SingleCustomerConfigViewDesign()
    {
        Design.read( this );
    }
}
