package com.kotlarz.frontend.presenter;

import com.kotlarz.backend.domain.EventEntity;
import com.kotlarz.backend.domain.FormatterConfigEntity;
import com.kotlarz.backend.domain.ReportEntity;
import com.kotlarz.backend.service.CustomerService;
import com.kotlarz.backend.service.ReportService;
import com.kotlarz.frontend.dto.EventDto;
import com.kotlarz.frontend.util.LogFormatter;
import com.kotlarz.frontend.util.ParametersUtil;
import com.kotlarz.frontend.view.events.EventsView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringComponent
@UIScope
public class EventsPresenter implements Presenter<EventsView> {
    public static final String REPORT_URL_CONST = "events";

    public static final Integer REPORT_ID_INDEX = 3;

    @Autowired
    private ReportService reportService;

    @Autowired
    private CustomerService customerService;

    @Override
    public void handleNavigation(ViewChangeListener.ViewChangeEvent event, EventsView view) {
        List<String> parameters = ParametersUtil.resolve(event);
        long reportId = Long.parseLong(parameters.get(REPORT_ID_INDEX));
        long customerId = Long.parseLong(parameters.get(ReportsPresenter.CUSTOMER_ID_INDEX));

        Optional<FormatterConfigEntity> optionalFormatterConfig = customerService.getFormatterConfig(customerId);

        if (optionalFormatterConfig.isPresent()) {
            LogFormatter formatter = new LogFormatter(optionalFormatterConfig.get());
            List<EventDto> events = reportService.getReport(reportId).getEvents().stream()
                    .map(eventEntity -> new EventDto(eventEntity))
                    .collect(Collectors.toList());

            String formattedLog = formatter.format(events).stream()
                    .collect(Collectors.joining("\r\n"));
            view.setLog(formattedLog);
        } else {

        }
    }

    @Override
    public void initView(EventsView view) {

    }
}
