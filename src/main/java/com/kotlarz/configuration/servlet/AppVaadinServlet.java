package com.kotlarz.configuration.servlet;

import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessagesProvider;
import com.vaadin.server.VaadinServletService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.server.SpringVaadinServlet;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

@SpringComponent("vaadinServlet")
public class AppVaadinServlet extends SpringVaadinServlet {
    @Autowired
    private VaadinSessionListener listener;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        VaadinServletService service = getService();
        service.addSessionInitListener(event -> listener.register(event.getSession()));
        service.addSessionDestroyListener(event -> listener.unregister(event.getSession()));
    }

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().setSystemMessagesProvider((SystemMessagesProvider) systemMessagesInfo -> {
            CustomizedSystemMessages messages = new CustomizedSystemMessages();
            messages.setSessionExpiredNotificationEnabled(false);
            messages.setCommunicationErrorNotificationEnabled(false);
            return messages;
        });
    }
}