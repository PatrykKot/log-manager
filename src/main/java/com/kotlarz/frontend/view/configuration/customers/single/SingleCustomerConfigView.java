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
        initCustomerNameField();
        initFormatterPatternField();
        initClearLogsField();
        initFillLogsField();
        initTokenField();
    }

    private void initTokenField() {
        binder.forField(tokenField)
                .bind(
                        CustomerDto::getToken,
                        CustomerDto::setToken
                );
    }

    private void initFillLogsField() {
        binder.forField(fillLogsCheckBox)
                .bind(
                        CustomerDto::getFillPattern,
                        CustomerDto::setFillPattern
                );
    }

    private void initClearLogsField() {
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
    }

    private void initFormatterPatternField() {
        binder.forField(formatterPatternField)
                .withValidator(new NotEmptyStringValidator())
                .bind(
                        CustomerDto::getPattern,
                        CustomerDto::setPattern
                );
    }

    private void initCustomerNameField() {
        binder.forField(customerNameField)
                .withValidator(new NotEmptyStringValidator())
                .bind(CustomerDto::getName, CustomerDto::setName);
    }

    public void onButtonClicked(Runnable call) {
        formButton.addClickListener(event -> call.run());
    }

    public void onDeleteButtonClicked(Consumer<CustomerDto> handler) {
        deleteButton.addClickListener(event -> handler.accept(readCustomer));
    }

    public void onGenerateTokenButtonClicked(Runnable handler) {
        generateTokenButton.addClickListener(event -> handler.run());
    }

    public void setToken(String token) {
        tokenField.setValue(token);
    }
}
