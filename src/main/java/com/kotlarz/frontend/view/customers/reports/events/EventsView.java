package com.kotlarz.frontend.view.customers.reports.events;

import com.kotlarz.frontend.presenter.customers.reports.events.EventsPresenter;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class EventsView extends EventsViewDesign implements View {
    @Autowired
    private EventsPresenter eventsPresenter;

    @PostConstruct
    private void init() {
        eventsPresenter.initView(this);
    }

    public void setLog(String log) {
        eventsTextArea.setValue(log);
    }

    public void addOnFiltersButtonClick(Button.ClickListener listener) {
        filtersButton.addClickListener(listener);
    }
}
