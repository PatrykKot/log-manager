package com.kotlarz.frontend.view.customers.reports.events.filters;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringComponent
@UIScope
public class FiltersView extends FiltersViewDesign {
    @PostConstruct
    private void init() {
    }

    public void setThreadItems(List<String> threads) {
        threadCombo.setItems(threads);
    }

    public void setUserItems(List<String> users) {
        userCombo.setItems(users);
    }

    public void setLevelItems(List<String> levels) {
        levelCombo.setItems(levels);
    }

    public void setClassItems(List<String> classes) {
        classCombo.setItems(classes);
    }
}
