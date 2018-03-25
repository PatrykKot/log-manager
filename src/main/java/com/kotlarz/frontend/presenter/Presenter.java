package com.kotlarz.frontend.presenter;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.AbstractLayout;

public interface Presenter<T extends AbstractLayout> {
    void initView(T view);

    default void handleNavigation(ViewChangeListener.ViewChangeEvent event, T view) {
    }
}
