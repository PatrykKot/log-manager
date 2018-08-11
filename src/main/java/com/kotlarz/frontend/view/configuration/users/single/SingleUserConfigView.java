package com.kotlarz.frontend.view.configuration.users.single;

import com.kotlarz.backend.domain.system.UserType;
import com.kotlarz.frontend.dto.UserDto;
import com.kotlarz.frontend.util.validator.NotEmptyStringValidator;
import com.kotlarz.frontend.util.validator.ValidatorWrapper;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class SingleUserConfigView
        extends SingleUserConfigViewDesign {
    protected Binder<UserDto> binder = new Binder<>();

    @Getter
    protected UserDto readUser;

    @PostConstruct
    void init() {
        initTypeCombo();
        initBinder();
    }

    private void initTypeCombo() {
        typeCombo.setItems(Arrays.asList(UserType.values()));
    }

    public void readBean(UserDto bean) {
        binder.readBean(bean);
        this.readUser = bean;
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

    public void onButtonClicked(Runnable call) {
        formButton.addClickListener(event -> call.run());
    }

    public void onDeleteButtonClicked(Consumer<UserDto> handler) {
        deleteButton.addClickListener(event -> handler.accept(readUser));
    }
}
