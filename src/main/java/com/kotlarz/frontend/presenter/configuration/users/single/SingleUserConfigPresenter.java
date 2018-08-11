package com.kotlarz.frontend.presenter.configuration.users.single;

import com.kotlarz.backend.service.system.UserService;
import com.kotlarz.frontend.dto.UserDto;
import com.kotlarz.frontend.view.configuration.users.single.EditUserView;
import com.kotlarz.frontend.view.configuration.users.single.SingleUserConfigView;
import com.vaadin.data.ValidationException;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Notification;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Consumer;

@SpringComponent
@UIScope
@Slf4j
public class SingleUserConfigPresenter {
    @Setter
    private Runnable onConfigFinished = () -> {
    };

    @Autowired
    private UserService userService;

    public void init(SingleUserConfigView createUserView) {
        init(createUserView, userDto -> {
            userService.create(userDto);
            Notification.show("User " + userDto.getUsername() + " added.");
        });
    }

    public void init(EditUserView editUserView) {
        init(editUserView, userDto -> {
            userDto.setId(editUserView.getReadUser().getId());
            userService.update(userDto);
            Notification.show("User " + userDto.getUsername() + " updated.");
        });

        editUserView.onDeleteButtonClicked(userDto -> {
            userService.delete(userDto.getId());
            Notification.show("User " + userDto.getUsername() + " deleted.");
            editUserView.close();
            onConfigFinished.run();
        });
    }

    private void init(SingleUserConfigView view, Consumer<UserDto> handle) {
        view.onButtonClicked(() -> {
            try {
                UserDto userDto = new UserDto();
                view.writeBean(userDto);
                handle.accept(userDto);
                view.close();
                onConfigFinished.run();
            } catch (ValidationException error) {
                log.error(error.getMessage(), error);
            }
        });
    }
}
