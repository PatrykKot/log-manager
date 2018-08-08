package com.kotlarz.frontend.view.login;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.util.function.BiConsumer;

@SpringView(name = Login.NAME)
@UIScope
public class Login extends VerticalLayout implements View {
    public static final String NAME = "login";

    private BiConsumer<String, String> callback = (i, j) -> {
    };

    public Login() {
        setMargin(true);
        setSpacing(true);

        TextField usernameField = new TextField("Username");
        addComponent(usernameField);

        PasswordField passwordField = new PasswordField("Password");
        addComponent(passwordField);

        Button login = new Button("Login", event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();

            callback.accept(username, password);
        });

        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        addComponent(login);
    }
}
