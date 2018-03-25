package com.kotlarz.frontend.presenter.customers;

import com.kotlarz.backend.service.CustomerService;
import com.kotlarz.frontend.dto.CustomerDto;
import com.kotlarz.frontend.presenter.Presenter;
import com.kotlarz.frontend.presenter.customers.reports.ReportsPresenter;
import com.kotlarz.frontend.presenter.customers.reports.events.EventsPresenter;
import com.kotlarz.frontend.util.ParametersUtil;
import com.kotlarz.frontend.util.PathBuilder;
import com.kotlarz.frontend.view.customers.CustomersView;
import com.kotlarz.frontend.view.customers.reports.ReportsView;
import com.kotlarz.frontend.view.customers.reports.events.EventsView;
import com.kotlarz.frontend.view.main.MainView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringNavigator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
@UIScope
public class CustomersPresenter implements Presenter<CustomersView> {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private SpringNavigator navigator;

    @Autowired
    private ReportsPresenter reportsPresenter;

    @Autowired
    private EventsPresenter eventsPresenter;

    @Autowired
    private ReportsView reportsView;

    @Autowired
    private EventsView eventsView;

    @Autowired
    private MainView mainView;

    @Override
    public void initView(CustomersView view) {
        view.setOnEnterEvent(event -> {
            List<String> parameters = ParametersUtil.resolve(event);
            if (parameters.isEmpty()) {
                initCustomersView(view);
            } else if(parameters.size() == 2){
                mainView.showView(reportsView);
                reportsPresenter.handleNavigation(event, reportsView);
            }
            else if(parameters.size() == 4) {
                mainView.showView(eventsView);
                eventsPresenter.handleNavigation(event, eventsView);
            }
        });
    }

    private void initCustomersView(CustomersView view) {
        initCustomersGrid(view);

        view.setOnCustomerSelected(customer -> navigator.navigateTo(PathBuilder.getInstance()
                .view(CustomersView.class)
                .param(customer.getName())
                .param(customer.getId())
                .build()));
    }

    private void initCustomersGrid(CustomersView view) {
        List<CustomerDto> customers = customerService.getCustomers().stream()
                .map(CustomerDto::new)
                .collect(Collectors.toList());
        view.setCustomers(customers);
    }
}
