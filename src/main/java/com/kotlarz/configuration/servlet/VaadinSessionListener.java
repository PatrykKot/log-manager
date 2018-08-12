package com.kotlarz.configuration.servlet;

import com.kotlarz.backend.domain.system.User;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
public class VaadinSessionListener {
    private List<VaadinSession> sessions = new LinkedList<>();

    public void register(VaadinSession session) {
        sessions.add(session);
    }

    public void unregister(VaadinSession session) {
        sessions.remove(session);
    }

    public List<VaadinSession> getSessionsForUser(Long userId) {
        return sessions.stream()
                .filter(session -> {
                    SecurityContext context = session.getAttribute(SecurityContext.class);
                    if (context != null) {
                        Authentication authentication = context.getAuthentication();
                        if (authentication != null) {
                            Object principal = authentication.getPrincipal();
                            if (principal instanceof User) {
                                return ((User) principal).getId().equals(userId);
                            }
                        }
                    }

                    return false;
                })
                .collect(Collectors.toList());
    }
}
