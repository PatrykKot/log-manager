package com.kotlarz.frontend.presenter.configuration.customers;

import com.kotlarz.backend.service.CustomerService;
import com.kotlarz.frontend.dto.CustomerDto;
import com.kotlarz.frontend.presenter.Presenter;
import com.kotlarz.frontend.util.ParametersUtil;
import com.kotlarz.frontend.view.configuration.customers.CustomersGridConfigView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
@UIScope
public class CustomersGridConfigPresenter implements Presenter<CustomersGridConfigView> {
    @Autowired
    private CustomerService customerService;

    @Override
    public void initView(CustomersGridConfigView view) {
        view.setOnEnterEvent(event -> {
            List<String> parameters = ParametersUtil.resolve(event);
            if (parameters.isEmpty()) {
                init(view);
            } else {
                throw new IllegalArgumentException("Not implemented yet");
            }
        });
    }

    private void init(CustomersGridConfigView view) {
        initCustomersGrid(view);
    }

    private void initCustomersGrid(CustomersGridConfigView view) {
        List<CustomerDto> customers = customerService.getCustomers().stream()
                .map(CustomerDto::new)
                .collect(Collectors.toList());
        view.setCustomers(customers);
    }
}
