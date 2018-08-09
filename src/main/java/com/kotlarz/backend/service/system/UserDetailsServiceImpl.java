package com.kotlarz.backend.service.system;

import com.kotlarz.backend.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with username " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPasswordHash(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }
}
