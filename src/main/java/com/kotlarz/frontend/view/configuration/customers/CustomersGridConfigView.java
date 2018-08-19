package com.kotlarz.frontend.view.configuration.customers;

import com.kotlarz.frontend.dto.CustomerDto;
import com.kotlarz.frontend.presenter.configuration.customers.CustomersGridConfigPresenter;
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

@SpringView(name = CustomersGridConfigView.NAME)
@ViewScope
public class CustomersGridConfigView extends CustomersGridConfigViewDesign implements View {
    public static final String NAME = "configuration/customers";

    @Autowired
    private CustomersGridConfigPresenter presenter;

    @Setter
    private Consumer<ViewChangeListener.ViewChangeEvent> onEnterEvent;

    @Setter
    private Consumer<CustomerDto> onCustomerClicked;

    @PostConstruct
    private void init() {
        customersGrid.addColumn(CustomerDto::getName).setCaption("Name");
        customersGrid.addColumn(CustomerDto::getClearLogsAfterDays).setCaption("Clear after [days]");
        customersGrid.addColumn(CustomerDto::getPattern).setCaption("Pattern");
        customersGrid.addColumn(CustomerDto::getFillPattern).setCaption("Fill to equal spaces");

        customersGrid.addItemClickListener(event -> {
            CustomerDto selectedItem = event.getItem();
            onCustomerClicked.accept(selectedItem);
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

    public void addOnAddNewCustomerButtonClick(Button.ClickListener listener) {
        addCustomerButton.addClickListener(listener);
    }
}
