package com.kotlarz.frontend.presenter.configuration.users;

import com.kotlarz.backend.domain.system.User;
import com.kotlarz.backend.service.logs.CustomerService;
import com.kotlarz.backend.service.system.UserService;
import com.kotlarz.configuration.security.exception.UserNotFoundException;
import com.kotlarz.frontend.dto.AvailableCustomerDto;
import com.kotlarz.frontend.dto.UserDto;
import com.kotlarz.frontend.presenter.Presenter;
import com.kotlarz.frontend.presenter.configuration.users.single.SingleUserConfigPresenter;
import com.kotlarz.frontend.ui.MainUI;
import com.kotlarz.frontend.view.configuration.users.UsersConfigurationView;
import com.kotlarz.frontend.view.configuration.users.single.CreateUserView;
import com.kotlarz.frontend.view.configuration.users.single.EditUserView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
@ViewScope
public class UsersConfigPresenter
        implements Presenter<UsersConfigurationView> {
    @Autowired
    private UserService userService;

    @Autowired
    private MainUI mainUI;

    @Autowired
    private CreateUserView createUserView;

    @Autowired
    private EditUserView editUserView;

    @Autowired
    private SingleUserConfigPresenter singleUserConfigPresenter;

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

            List<AvailableCustomerDto> allCustomers = customerService.getCustomers().stream()
                    .map(AvailableCustomerDto::new)
                    .collect(Collectors.toList());
            editUserView.setAllCustomers(allCustomers);

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
        view.onAddNewUserButtonClick(event -> {
            List<AvailableCustomerDto> allCustomers = customerService.getCustomers().stream()
                    .map(AvailableCustomerDto::new)
                    .collect(Collectors.toList());
            createUserView.setAllCustomers(allCustomers);

            mainUI.addWindow(createUserView);
        });

        singleUserConfigPresenter.setOnConfigFinished(() -> loadUsers(view));
    }
}
