package com.kotlarz.frontend.view.customers;

import com.kotlarz.frontend.dto.CustomerDto;
import com.kotlarz.frontend.presenter.customers.CustomersPresenter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@SpringView(name = CustomersView.NAME)
@ViewScope
public class CustomersView extends CustomersViewDesign implements View {
    public static final String NAME = "customers";

    @Autowired
    private CustomersPresenter presenter;

    @Setter
    private Consumer<ViewChangeListener.ViewChangeEvent> onEnterEvent = ($) -> {
    };

    @Setter
    private Consumer<CustomerDto> onCustomerSelected = ($) -> {
    };

    @PostConstruct
    private void init() {
        customersGrid.addColumn(CustomerDto::getName).setCaption("Name");

        customersGrid.addSelectionListener(selection -> {
            Optional<CustomerDto> firstSelectedItem = selection.getFirstSelectedItem();
            firstSelectedItem.ifPresent(customerDto -> onCustomerSelected.accept(customerDto));
        });

        presenter.initView(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        onEnterEvent.accept(event);
    }

    public void setCustomers(List<CustomerDto> customers) {
        customersGrid.setItems(customers);
    }
}
