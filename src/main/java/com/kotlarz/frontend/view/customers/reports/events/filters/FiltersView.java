package com.kotlarz.frontend.view.customers.reports.events.filters;

import com.kotlarz.frontend.dto.EventFilter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

import java.util.List;
import java.util.function.Consumer;

@SpringComponent
@UIScope
public class FiltersView extends FiltersViewDesign {
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

    public void clearFilters() {
        threadCombo.clear();
        userCombo.clear();
        levelCombo.clear();
        classCombo.clear();
    }

    public void setOnFilterUpdated(Consumer<EventFilter> event) {
        threadCombo.addValueChangeListener(thread -> {
            event.accept(buildFilter());
        });

        userCombo.addValueChangeListener(thread -> {
            event.accept(buildFilter());
        });

        levelCombo.addValueChangeListener(thread -> {
            event.accept(buildFilter());
        });

        classCombo.addValueChangeListener(thread -> {
            event.accept(buildFilter());
        });
    }

    private EventFilter buildFilter() {
        return EventFilter.builder()
                .thread(threadCombo.getValue())
                .user(userCombo.getValue())
                .level(levelCombo.getValue())
                .classname(classCombo.getValue())
                .build();
    }
}
