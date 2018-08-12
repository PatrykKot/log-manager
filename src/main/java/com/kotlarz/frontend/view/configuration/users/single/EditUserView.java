package com.kotlarz.frontend.view.configuration.users.single;

import com.kotlarz.frontend.presenter.configuration.users.single.SingleUserConfigPresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringComponent
@UIScope
public class EditUserView
        extends SingleUserConfigView {
    @Autowired
    private SingleUserConfigPresenter presenter;

    @Override
    @PostConstruct
    void init() {
        super.init();
        formButton.setCaption("Save");

        presenter.init(this);
    }
}

