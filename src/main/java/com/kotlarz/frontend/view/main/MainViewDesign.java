package com.kotlarz.frontend.view.main;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.Design;
import lombok.Getter;

@DesignRoot
@Getter
public class MainViewDesign extends HorizontalLayout {
    protected Panel contentPanel;
    protected Button customersButton;
    protected Button configurationButton;
    protected Button dashboardButton;

    public MainViewDesign() {
        Design.read(this);
    }
}
