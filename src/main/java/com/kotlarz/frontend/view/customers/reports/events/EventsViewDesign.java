package com.kotlarz.frontend.view.customers.reports.events;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class EventsViewDesign extends VerticalLayout {
    protected TextArea eventsTextArea;

    protected Button filtersButton;

    protected Button downloadLogFileButton;

    public EventsViewDesign() {
        Design.read(this);
    }
}
