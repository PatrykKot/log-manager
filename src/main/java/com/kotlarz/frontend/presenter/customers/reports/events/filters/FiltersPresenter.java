package com.kotlarz.frontend.presenter.customers.reports.events.filters;

import com.kotlarz.frontend.dto.EventDto;
import com.kotlarz.frontend.dto.EventFilter;
import com.kotlarz.frontend.view.customers.reports.events.filters.FiltersView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringComponent
@UIScope
public class FiltersPresenter {
    @Setter
    private Consumer<List<EventDto>> onFilterUpdated;

    public void initView(FiltersView view, List<EventDto> events) {
        view.clearFilters();
        view.setThreadItems(map(events, EventDto::getThreadName));
        view.setUserItems(map(events, EventDto::getUsername));
        view.setLevelItems(map(events, EventDto::getLevel));
        view.setClassItems(map(events, EventDto::getClassname));

        view.setOnFilterUpdated(filter -> {
            List<EventDto> filtered = resolveFilter(events, filter);
            if (onFilterUpdated != null) {
                onFilterUpdated.accept(filtered);
            }
        });
    }

    private List<String> map(Collection<EventDto> events, Function<EventDto, String> mapper) {
        return events.stream()
                .map(mapper)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    private List<EventDto> resolveFilter(List<EventDto> events, EventFilter filter) {
        return events.stream()
                .filter(event -> {
                    if (StringUtils.isNotEmpty(filter.getThread())) {
                        return event.getThreadName().contains(filter.getThread());
                    }
                    {
                        return true;
                    }
                })
                .filter(event -> {
                    if (StringUtils.isNotEmpty(filter.getUser())) {
                        return event.getUsername().contains(filter.getUser());
                    }
                    {
                        return true;
                    }
                })
                .filter(event -> {
                    if (StringUtils.isNotEmpty(filter.getClassname())) {
                        return event.getClassname().contains(filter.getClassname());
                    }
                    {
                        return true;
                    }
                })
                .filter(event -> {
                    if (StringUtils.isNotEmpty(filter.getLevel())) {
                        return event.getLevel().contains(filter.getLevel());
                    }
                    {
                        return true;
                    }
                })
                .collect(Collectors.toList());
    }

}
