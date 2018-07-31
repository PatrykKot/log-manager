package com.kotlarz.frontend.view.configuration.customers.single;

import com.kotlarz.frontend.dto.CustomerDto;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;

import javax.annotation.PostConstruct;

public abstract class SingleCustomerConfigView
        extends SingleCustomerConfigViewDesign {
    private Binder<CustomerDto> binder = new Binder<>();

    @PostConstruct
    void init() {
        initBinder();
    }

    public void readBean(CustomerDto bean) {
        binder.readBean(bean);
    }

    public void writeBean(CustomerDto bean) throws ValidationException {
        binder.writeBean(bean);
    }

    private void initBinder() {
        binder.forField(customerNameField)
                .bind(CustomerDto::getName, CustomerDto::setName);
        binder.forField(formatterPatternField)
                .bind(
                        customer -> customer.getPattern(),
                        (customer, value) -> customer.setPattern(value)
                );
        binder.forField(clearLogsAfterDaysField)
                .bind(
                        customer -> customer.getClearLogsAfterDays().toString(),
                        (customer, value) -> customer.setClearLogsAfterDays(Long.parseLong(value))
                );
        binder.forField(fillLogsCheckBox)
                .bind(
                        customer -> customer.getFillPattern(),
                        (customer, value) -> customer.setFillPattern(value)
                );
    }
}
