package com.kotlarz.frontend.presenter.configuration.customers;

import com.kotlarz.backend.service.logs.CustomerService;
import com.kotlarz.frontend.dto.CustomerDto;
import com.kotlarz.frontend.presenter.Presenter;
import com.kotlarz.frontend.presenter.configuration.customers.single.SingleCustomerConfigPresenter;
import com.kotlarz.frontend.ui.MainUI;
import com.kotlarz.frontend.util.ParametersUtil;
import com.kotlarz.frontend.view.configuration.customers.CustomersGridConfigView;
import com.kotlarz.frontend.view.configuration.customers.single.CreateCustomerView;
import com.kotlarz.frontend.view.configuration.customers.single.EditCustomerView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
@UIScope
public class CustomersGridConfigPresenter
        implements Presenter<CustomersGridConfigView> {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private MainUI mainUI;

    @Autowired
    private EditCustomerView editCustomerView;

    @Autowired
    private CreateCustomerView createCustomerView;

    @Autowired
    private SingleCustomerConfigPresenter singleCustomerConfigPresenter;

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
        loadCustomers(view);
        initAddCustomerButton(view);
    }

    private void initCustomersGrid(CustomersGridConfigView view) {
        view.setOnCustomerClicked(customerDto -> {
            mainUI.addWindow(editCustomerView);
            editCustomerView.readBean(customerDto);
        });
    }

    private void loadCustomers(CustomersGridConfigView view) {
        List<CustomerDto> customers = customerService.getCustomers().stream()
                .map(CustomerDto::new)
                .collect(Collectors.toList());
        view.setCustomers(customers);
    }

    private void initAddCustomerButton(CustomersGridConfigView view) {
        view.addOnAddNewCustomerButtonClick(event -> mainUI.addWindow(createCustomerView));
        singleCustomerConfigPresenter.setOnConfigFinished(() -> {
            loadCustomers(view);
        });
    }
}
