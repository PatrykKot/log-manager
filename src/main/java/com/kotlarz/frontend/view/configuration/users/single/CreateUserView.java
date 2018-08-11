package com.kotlarz.frontend.view.configuration.users.single;

import com.kotlarz.frontend.dto.UserDto;
import com.kotlarz.frontend.presenter.configuration.users.single.SingleUserConfigPresenter;
import com.kotlarz.frontend.util.validator.NotEmptyStringValidator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.AbstractOrderedLayout;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class CreateUserView
        extends SingleUserConfigView {
    @Autowired
    private SingleUserConfigPresenter presenter;

    @Override
    @PostConstruct
    void init() {
        super.init();
        binder.forField(passwordField)
                .withValidator(new NotEmptyStringValidator())
                .bind(userDto -> StringUtils.EMPTY, UserDto::setRawPassword);

        formButton.setCaption("Create");
        AbstractOrderedLayout parent = (AbstractOrderedLayout) deleteButton.getParent();
        parent.removeComponent(deleteButton);

        presenter.init(this);
    }
}
