package com.kotlarz.frontend.view.main;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.declarative.Design;
import lombok.Getter;

@DesignRoot
@Getter
public class MainViewDesign extends HorizontalLayout {
    protected Panel contentPanel;
    protected Button customersButton;
    protected Button configurationButton;
    protected Button dashboardButton;
    protected Button logoutButton;
    protected AbstractOrderedLayout menuButtonsLayout;

    public MainViewDesign() {
        Design.read(this);
    }
}
