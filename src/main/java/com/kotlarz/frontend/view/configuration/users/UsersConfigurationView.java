package com.kotlarz.frontend.view.configuration.users;

import com.kotlarz.frontend.dto.UserDto;
import com.kotlarz.frontend.presenter.configuration.users.UsersConfigPresenter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Consumer;

@SpringView(name = UsersConfigurationView.NAME)
@ViewScope
public class UsersConfigurationView
        extends UsersConfigurationViewDesign implements View {
    public static final String NAME = "configuration/users";

    @Autowired
    private UsersConfigPresenter presenter;

    @Setter
    private Consumer<ViewChangeListener.ViewChangeEvent> onEnterEvent;

    @Setter
    private Consumer<UserDto> onUserClicked;

    @PostConstruct
    private void init() {
        usersGrid.addColumn(UserDto::getUsername).setCaption("Username");
        usersGrid.addColumn(UserDto::getType).setCaption("Type");

        usersGrid.addItemClickListener(event -> {
            UserDto selectedItem = event.getItem();
            onUserClicked.accept(selectedItem);
        });

        presenter.initView(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (onEnterEvent != null)
            onEnterEvent.accept(event);
    }

    public void setUsers(List<UserDto> users) {
        usersGrid.setItems(users);
    }

    public void onAddNewUserButtonClick(Button.ClickListener listener) {
        addUserButton.addClickListener(listener);
    }
}
