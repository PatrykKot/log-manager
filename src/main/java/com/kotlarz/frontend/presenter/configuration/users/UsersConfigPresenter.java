package com.kotlarz.frontend.presenter.configuration.users;

import com.kotlarz.backend.domain.system.User;
import com.kotlarz.backend.service.logs.CustomerService;
import com.kotlarz.backend.service.system.UserService;
import com.kotlarz.configuration.security.exception.UserNotFoundException;
import com.kotlarz.frontend.dto.UserDto;
import com.kotlarz.frontend.presenter.Presenter;
import com.kotlarz.frontend.presenter.configuration.users.single.SingleUserConfigPresenter;
import com.kotlarz.frontend.ui.MainUI;
import com.kotlarz.frontend.view.configuration.users.UsersConfigurationView;
import com.kotlarz.frontend.view.configuration.users.single.EditUserView;
import com.kotlarz.frontend.view.configuration.users.single.SingleUserConfigViewDesign;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
@UIScope
public class UsersConfigPresenter
        implements Presenter<UsersConfigurationView> {
    @Autowired
    private UserService userService;

    @Autowired
    private MainUI mainUI;

    @Autowired
    private SingleUserConfigPresenter singleUserConfigPresenter;

    @Autowired
    private SingleUserConfigViewDesign createUserView;

    @Autowired
    private EditUserView editUserView;

    @Autowired
    private CustomerService customerService;

    @Override
    public void initView(UsersConfigurationView view) {
        init(view);
    }

    private void init(UsersConfigurationView view) {
        initUsersGrid(view);
        loadUsers(view);
        initAddUserButton(view);
    }

    private void initUsersGrid(UsersConfigurationView view) {
        view.setOnUserClicked(userDto -> {
            mainUI.addWindow(editUserView);

            User user = userService.getUserWithCustomers(userDto.getId()).orElseThrow(UserNotFoundException::new);
            editUserView.readBean(new UserDto(user, true));
        });
    }

    private void loadUsers(UsersConfigurationView view) {
        List<UserDto> customers = userService.getUsers().stream()
                .map(domain -> new UserDto(domain, false))
                .collect(Collectors.toList());
        view.setUsers(customers);
    }

    private void initAddUserButton(UsersConfigurationView view) {
        view.onAddNewUserButtonClick(event -> mainUI.addWindow(createUserView));
        singleUserConfigPresenter.setOnConfigFinished(() -> {
            loadUsers(view);
        });
    }
}
