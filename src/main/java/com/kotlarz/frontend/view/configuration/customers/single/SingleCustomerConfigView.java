package com.kotlarz.frontend.view.configuration.customers.single;

import com.kotlarz.frontend.dto.CustomerDto;
import com.kotlarz.frontend.util.validator.NotEmptyStringValidator;
import com.kotlarz.frontend.util.validator.PositiveIntegerValidator;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;

public abstract class SingleCustomerConfigView
        extends SingleCustomerConfigViewDesign {
    private Binder<CustomerDto> binder = new Binder<>();

    @Getter
    protected CustomerDto readCustomer;

    @PostConstruct
    void init() {
        initBinder();
    }

    public void readBean(CustomerDto bean) {
        binder.readBean(bean);
        this.readCustomer = bean;
    }

    public void writeBean(CustomerDto bean) throws ValidationException {
        binder.writeBean(bean);
    }

    private void initBinder() {
        binder.forField(customerNameField)
                .withValidator(new NotEmptyStringValidator())
                .bind(CustomerDto::getName, CustomerDto::setName);
        binder.forField(formatterPatternField)
                .withValidator(new NotEmptyStringValidator())
                .bind(
                        customer -> customer.getPattern(),
                        (customer, value) -> customer.setPattern(value)
                );
        binder.forField(clearLogsAfterDaysField)
                .withValidator(new PositiveIntegerValidator())
                .bind(
                        customer -> {
                            if (customer.getClearLogsAfterDays() == null) {
                                return StringUtils.EMPTY;
                            } else {
                                return customer.getClearLogsAfterDays().toString();
                            }
                        },
                        (customer, value) -> {
                            try {
                                customer.setClearLogsAfterDays(Long.parseLong(value));
                            } catch (NumberFormatException ex) {
                                customer.setClearLogsAfterDays(0L);
                            }
                        }
                );
        binder.forField(fillLogsCheckBox)
                .bind(
                        customer -> customer.getFillPattern(),
                        (customer, value) -> customer.setFillPattern(value)
                );
    }

    public void onButtonClicked(Runnable call) {
        formButton.addClickListener(event -> call.run());
    }

    public void onDeleteButtonClicked(Consumer<CustomerDto> handler) {
        deleteButton.addClickListener(event -> handler.accept(readCustomer));
    }
}
