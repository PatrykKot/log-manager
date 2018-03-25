package com.kotlarz.frontend.view.events;

import com.kotlarz.frontend.presenter.EventsPresenter;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class EventsView extends EventsViewDesign implements View {
    @Autowired
    private EventsPresenter eventsPresenter;

    @Autowired
    private void init() {
        eventsPresenter.initView(this);
    }

    public void setLog(String log) {
        eventsTextArea.setValue(log);
    }
}
