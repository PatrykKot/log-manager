package com.kotlarz.frontend.view.main;

import com.kotlarz.backend.domain.system.UserType;
import com.kotlarz.configuration.security.service.SecurityService;
import com.kotlarz.frontend.presenter.MainPresenter;
import com.kotlarz.frontend.view.login.LoginView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringViewDisplay
@UIScope
public class MainView
        extends MainViewDesign
        implements ViewDisplay {

    @Autowired
    private MainPresenter presenter;

    @Autowired
    private SecurityService securityService;

    @PostConstruct
    private void init() {
        presenter.initView(this);
    }

    @Override
    public void showView(View view) {
        changeButtonsVisibility(view);
        contentPanel.setContent(view.getViewComponent());
    }

    private void changeButtonsVisibility(View view) {
        Boolean isLoginView = view instanceof LoginView;
        menuButtonsLayout.setVisible(!isLoginView);

        if (!isLoginView) {
            boolean isAdmin = securityService.isTypeOf(UserType.ADMIN);
            configurationButton.setVisible(isAdmin);
        }
    }
}
