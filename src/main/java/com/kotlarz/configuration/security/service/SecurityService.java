package com.kotlarz.configuration.security.service;

import com.kotlarz.backend.domain.system.UserEntity;
import com.kotlarz.backend.domain.system.UserType;
import com.kotlarz.backend.service.system.UserService;
import com.kotlarz.configuration.security.exception.UserIsNotLoggedInException;
import com.kotlarz.configuration.security.exception.UserNotFoundException;
import com.kotlarz.configuration.servlet.VaadinSessionListener;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.Cookie;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@SpringComponent
public class SecurityService {
    private static String TOKEN_COOKIE_NAME = "rememberMe";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private VaadinSessionListener sessionListener;

    @Autowired
    private TokenRepository tokenRepository;

    public boolean isLoggedIn() {
        return getAuthentication()
                .map(Authentication::isAuthenticated)
                .orElse(false);
    }

    public Authentication logIn(String username, String password) {
        Authentication token = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(token);
        return token;
    }

    public void logOut() {
        getPrincipal().ifPresent(principal -> {
            Long currentUserId = ((UserEntity) principal).getId();
            tokenRepository.invalidate(currentUserId);
        });

        SecurityContextHolder.clearContext();
    }

    public void logOut(Long userId) {
        sessionListener.getSessionsForUser(userId).forEach(VaadinSession::close);
        tokenRepository.invalidate(userId);
    }

    public Long getCurrentUserId() {
        Object principal = getPrincipal().orElseThrow(UserIsNotLoggedInException::new);
        return ((UserEntity) principal).getId();
    }

    private Optional<Object> getPrincipal() {
        return getAuthentication()
                .map(Authentication::getPrincipal);
    }


    public boolean isTypeOf(UserType type) {
        return getAuthentication()
                .map(Authentication::getAuthorities)
                .map(grantedAuthorities -> grantedAuthorities.stream()
                        .anyMatch(authority -> authority.getAuthority().equals(type.name())))
                .orElse(false);
    }

    private Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    public void rememberMe(Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        setCookie(createCookie(user.getId()));
    }

    private void setCookie(Cookie cookie) {
        VaadinResponse response = VaadinService.getCurrentResponse();
        response.addCookie(cookie);
    }

    private Cookie createCookie(Long userId) {
        Cookie myCookie = new Cookie(TOKEN_COOKIE_NAME, tokenRepository.store(userId));
        myCookie.setMaxAge(TokenRepository.TOKEN_VALID_TIME_SEC.intValue());
        myCookie.setPath("/");
        return myCookie;
    }

    public void tryAutoLogin() {
        Stream.of(VaadinService.getCurrentRequest().getCookies())
                .filter(cookie -> cookie.getName().equals(TOKEN_COOKIE_NAME))
                .findFirst()
                .ifPresent(cookie -> {
                    String token = cookie.getValue();
                    tokenRepository.get(token).ifPresent(userId -> {
                        UserEntity user = userService.getUser(userId).orElseThrow(UserNotFoundException::new);
                        Authentication authentication = new RememberMeAuthenticationToken(token, user, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    });
                });
    }
}
