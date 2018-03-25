package com.kotlarz.frontend.view.customers;

import com.kotlarz.frontend.dto.CustomerDto;
import com.kotlarz.frontend.presenter.customers.CustomersPresenter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@SpringView(name = CustomersView.NAME)
@UIScope
@Getter
@Setter
public class CustomersView extends CustomersViewDesign implements View {
    public static final String NAME = "customers";

    @Autowired
    private CustomersPresenter presenter;

    private Consumer<ViewChangeListener.ViewChangeEvent> onEnterEvent;

    private Consumer<CustomerDto> onCustomerSelected;

    @PostConstruct
    private void init() {
        customersGrid.addColumn(CustomerDto::getName).setCaption("Name");
        customersGrid.addSelectionListener(selection -> {
            if (onCustomerSelected != null) {
                Optional<CustomerDto> firstSelectedItem = selection.getFirstSelectedItem();
                firstSelectedItem.ifPresent(customerDto -> onCustomerSelected.accept(customerDto));
            }
        });

        presenter.initView(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (onEnterEvent != null)
            onEnterEvent.accept(event);
    }

    public void setCustomers(List<CustomerDto> customers) {
        customersGrid.setItems(customers);
    }
}
