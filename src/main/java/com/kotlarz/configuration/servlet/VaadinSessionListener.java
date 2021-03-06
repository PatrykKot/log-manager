package com.kotlarz.configuration.servlet;

import com.kotlarz.backend.domain.system.UserEntity;
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

    void register(VaadinSession session) {
        sessions.add(session);
    }

    void unregister(VaadinSession session) {
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
                            if (principal instanceof UserEntity) {
                                return ((UserEntity) principal).getId().equals(userId);
                            }
                        }
                    }

                    return false;
                })
                .collect(Collectors.toList());
    }
}
