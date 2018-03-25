package com.kotlarz.frontend.view.events;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

@DesignRoot
public class EventsViewDesign extends VerticalLayout {
    protected TextArea eventsTextArea;
    public EventsViewDesign() {
        Design.read(this);
    }
}
