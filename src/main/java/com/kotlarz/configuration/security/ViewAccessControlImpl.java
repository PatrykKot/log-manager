package com.kotlarz.configuration.security;

import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.ui.UI;
import org.springframework.stereotype.Component;

@Component
public class ViewAccessControlImpl implements ViewAccessControl {

    @Override
    public boolean isAccessGranted(UI ui, String beanName) {
        // TODO view access
        return true;
    }
}
