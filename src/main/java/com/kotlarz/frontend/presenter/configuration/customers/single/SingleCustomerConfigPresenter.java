package com.kotlarz.frontend.presenter.configuration.customers.single;

import com.kotlarz.backend.service.logs.CustomerService;
import com.kotlarz.frontend.dto.CustomerDto;
import com.kotlarz.frontend.view.configuration.customers.single.CreateCustomerView;
import com.kotlarz.frontend.view.configuration.customers.single.EditCustomerView;
import com.kotlarz.frontend.view.configuration.customers.single.SingleCustomerConfigView;
import com.vaadin.data.ValidationException;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Notification;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.function.Consumer;

@SpringComponent
@ViewScope
@Slf4j
public class SingleCustomerConfigPresenter {
    @Setter
    private Runnable onConfigFinished = () -> {
    };

    @Autowired
    private CustomerService customerService;

    public void init(CreateCustomerView createCustomerView) {
        init(createCustomerView, customerDto -> {
            customerService.create(customerDto.toEntity());
            Notification.show("Customer " + customerDto.getName() + " added.");
        });
    }

    public void init(EditCustomerView editCustomerView) {
        init(editCustomerView, customerDto -> {
            customerDto.setId(editCustomerView.getReadCustomer().getId());
            customerService.update(customerDto);
            Notification.show("Customer " + customerDto.getName() + " updated.");
        });

        editCustomerView.onDeleteButtonClicked(customerDto -> {
            customerService.delete(customerDto.getId());
            Notification.show("Customer " + customerDto.getName() + " deleted.");
            editCustomerView.close();
            onConfigFinished.run();
        });
    }

    private void init(SingleCustomerConfigView view, Consumer<CustomerDto> handle) {
        view.onButtonClicked(() -> {
            try {
                CustomerDto customerDto = new CustomerDto();
                view.writeBean(customerDto);
                handle.accept(customerDto);
                view.close();
                onConfigFinished.run();
            } catch (ValidationException error) {
                log.error(error.getMessage(), error);
            }
        });

        view.onGenerateTokenButtonClicked(() -> view.setToken(UUID.randomUUID().toString()));
    }
}
