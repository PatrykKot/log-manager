package com.kotlarz.frontend.view.configuration.users.single;

import com.kotlarz.backend.domain.logs.CustomerEntity;
import com.kotlarz.backend.domain.system.UserType;
import com.kotlarz.backend.service.logs.CustomerService;
import com.kotlarz.frontend.dto.AvailableCustomerDto;
import com.kotlarz.frontend.dto.UserDto;
import com.kotlarz.frontend.util.validator.NotEmptyStringValidator;
import com.kotlarz.frontend.util.validator.ValidatorWrapper;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ButtonRenderer;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class SingleUserConfigView
        extends SingleUserConfigViewDesign {
    protected Binder<UserDto> binder = new Binder<>();

    @Getter
    protected UserDto readUser;

    @Autowired
    private CustomerService customerService;

    @PostConstruct
    void init() {
        initTypeCombo();
        initBinder();
        initCustomersGrid();
    }

    private void initTypeCombo() {
        typeCombo.setItems(Arrays.asList(UserType.values()));
        typeCombo.setEmptySelectionAllowed(false);
        typeCombo.addValueChangeListener(event -> {
            //availableCustomersGrid.setVisible(event.getValue() != UserType.ADMIN);
        });
    }

    public void readBean(UserDto bean) {
        binder.readBean(bean);
        this.readUser = bean;

        boolean isAdmin = bean.getType() == UserType.ADMIN;
        availableCustomersGrid.setVisible(!isAdmin);
        availableCustomersGrid.setItems(bean.getAvailableCustomers());
    }

    public void writeBean(UserDto bean) throws ValidationException {
        binder.writeBean(bean);
    }

    private void initBinder() {
        binder.forField(usernameField)
                .withValidator(new NotEmptyStringValidator())
                .bind(UserDto::getUsername, UserDto::setUsername);

        binder.forField(passwordField)
                .bind(userDto -> StringUtils.EMPTY, UserDto::setRawPassword);

        binder.forField(typeCombo)
                .withValidator(ValidatorWrapper.create("Field cannot be null", Objects::nonNull))
                .bind(UserDto::getType, UserDto::setType);
    }

    private void initCustomersGrid() {
        List<CustomerEntity> customers = customerService.getCustomers();

        availableCustomersGrid.setSelectionMode(Grid.SelectionMode.NONE);
        availableCustomersGrid
                .addComponentColumn(availableCustomerDto -> {
                    ComboBox<AvailableCustomerDto> combo = new ComboBox<>();
                    combo.setEmptySelectionAllowed(false);
                    combo.setItemCaptionGenerator(AvailableCustomerDto::getName);
                    combo.setItems(customers.stream()
                            .map(AvailableCustomerDto::new)
                            .collect(Collectors.toList()));
                    combo.setSelectedItem(availableCustomerDto);

                    combo.addValueChangeListener(event -> {
                        AvailableCustomerDto newValue = event.getValue();
                        AvailableCustomerDto oldValue = event.getOldValue();
                        List<AvailableCustomerDto> availableCustomers = readUser.getAvailableCustomers();

                        int rowNumber = availableCustomers.indexOf(oldValue);
                        availableCustomers.set(rowNumber, newValue);
                        availableCustomersGrid.setItems(availableCustomers);
                    });

                    return combo;
                })
                .setCaption("Customer");

        availableCustomersGrid
                .addColumn(customer -> "Delete")
                .setRenderer(new ButtonRenderer<AvailableCustomerDto>(clickEvent -> {
                    List<AvailableCustomerDto> availableCustomers = readUser.getAvailableCustomers();
                    availableCustomers.remove(clickEvent.getItem());
                    availableCustomersGrid.setItems(availableCustomers);
                }))
                .setCaption("Action");

        addCustomerButton.addClickListener(event -> {
            List<AvailableCustomerDto> availableCustomers = readUser.getAvailableCustomers();
            if (availableCustomers.stream()
                    .map(AvailableCustomerDto::getId)
                    .noneMatch(Objects::isNull)) {
                availableCustomers.add(new AvailableCustomerDto());
                availableCustomersGrid.setItems(availableCustomers);
            }
        });
    }

    public void onButtonClicked(Runnable call) {
        formButton.addClickListener(event -> call.run());
    }

    public void onDeleteButtonClicked(Consumer<UserDto> handler) {
        deleteButton.addClickListener(event -> handler.accept(readUser));
    }
}
