package com.kotlarz.frontend.view.configuration.users.single;

import com.kotlarz.backend.domain.system.UserType;
import com.kotlarz.frontend.dto.AvailableCustomerDto;
import com.kotlarz.frontend.dto.UserDto;
import com.kotlarz.frontend.util.validator.NotEmptyStringValidator;
import com.kotlarz.frontend.util.validator.ValidatorWrapper;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.Grid;
import com.vaadin.ui.components.grid.GridSelectionModel;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class SingleUserConfigView
        extends SingleUserConfigViewDesign {
    protected Binder<UserDto> binder = new Binder<>();

    @Getter
    protected UserDto readUser;

    @Getter
    protected List<AvailableCustomerDto> allCustomers;

    @PostConstruct
    void init() {
        initTypeCombo();
        initBinder();
        initCustomersGrid();
    }

    private void initTypeCombo() {
        typeCombo.setItems(Arrays.asList(UserType.values()));
        typeCombo.setEmptySelectionAllowed(false);
    }

    public void readBean(UserDto bean) {
        binder.readBean(bean);
        this.readUser = bean;

        setSelected(bean.getAvailableCustomers());
    }

    public void writeBean(UserDto bean) throws ValidationException {
        binder.writeBean(bean);
        bean.setAvailableCustomers(getSelected());
    }

    private List<AvailableCustomerDto> getSelected() {
        GridSelectionModel<AvailableCustomerDto> model = availableCustomersGrid.getSelectionModel();
        return new LinkedList<>(model.getSelectedItems());
    }

    private void setSelected(List<AvailableCustomerDto> selected) {
        GridSelectionModel<AvailableCustomerDto> model = availableCustomersGrid.getSelectionModel();
        allCustomers.stream()
                .filter(item -> selected.stream()
                        .anyMatch(selectedItem -> selectedItem.getId().equals(item.getId())))
                .forEach(model::select);
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
        availableCustomersGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        availableCustomersGrid.addColumn(AvailableCustomerDto::getName)
                .setCaption("Name");
    }

    public void onButtonClicked(Runnable call) {
        formButton.addClickListener(event -> call.run());
    }

    public void onDeleteButtonClicked(Consumer<UserDto> handler) {
        deleteButton.addClickListener(event -> handler.accept(readUser));
    }

    public void setAllCustomers(List<AvailableCustomerDto> allCustomers) {
        this.allCustomers = allCustomers;
        availableCustomersGrid.setItems(allCustomers);
    }
}
