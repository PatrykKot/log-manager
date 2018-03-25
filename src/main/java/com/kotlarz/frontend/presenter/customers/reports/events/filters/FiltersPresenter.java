package com.kotlarz.frontend.presenter.customers.reports.events.filters;

import com.kotlarz.frontend.dto.EventDto;
import com.kotlarz.frontend.view.customers.reports.events.filters.FiltersView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringComponent
@UIScope
public class FiltersPresenter {
    private static List<String> map(Collection<EventDto> events, Function<EventDto, String> mapper) {
        return events.stream()
                .map(mapper)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public void initView(FiltersView view, List<EventDto> events) {
        view.setThreadItems(map(events, EventDto::getThreadName));
        view.setUserItems(map(events, EventDto::getUsername));
        view.setLevelItems(map(events, EventDto::getLevel));
        view.setClassItems(map(events, EventDto::getClassname));
    }
}
