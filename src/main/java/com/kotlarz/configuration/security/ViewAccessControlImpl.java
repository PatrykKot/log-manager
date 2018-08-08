package com.kotlarz.configuration.security;

import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.ui.UI;
import org.springframework.stereotype.Component;

/**
 * This demonstrates how you can control access to views.
 */
@Component
public class ViewAccessControlImpl implements ViewAccessControl {

    @Override
    public boolean isAccessGranted(UI ui, String beanName) {
        return true;

        /*if (beanName.equals("adminView")) {
            return SecurityUtils.hasRole(UserType.ADMIN.toString());
        } else {
            return SecurityUtils.hasRole(UserType.STANDARD.toString());
        }*/
    }
}
