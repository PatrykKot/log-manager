package com.kotlarz.frontend.view.login;

import com.kotlarz.frontend.presenter.login.LoginPresenter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.AbstractField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.function.BiConsumer;

@SpringView(name = LoginView.NAME)
@ViewScope
public class LoginView
        extends LoginViewDesign
        implements View {
    public static final String NAME = "login";

    @Autowired
    private LoginPresenter presenter;

    @PostConstruct
    public void init() {
        presenter.initView(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        usernameField.focus();
    }

    public void setOnLoginClick(BiConsumer<String, String> handler) {
        Runnable action = () -> handler.accept(usernameField.getValue(), passwordField.getValue());

        loginButton.addClickListener(event -> action.run());
        onEnterClicked(usernameField, action);
        onEnterClicked(passwordField, action);
    }

    public boolean isRememberMeSelected() {
        return rememberMeCheckbox.getValue();
    }

    private void onEnterClicked(AbstractField<?> field, Runnable handler) {
        field.addShortcutListener(new ShortcutListener(StringUtils.EMPTY, ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                handler.run();
            }
        });
    }
}
