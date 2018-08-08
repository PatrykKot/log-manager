package com.kotlarz.configuration.security;

import com.kotlarz.configuration.Application;
import com.kotlarz.frontend.view.login.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_URL = "/" + Application.APP_URL + "/" + Login.NAME;

    static {
        //SecurityContextHolder.setStrategyName(VaadinSessionSecurityContextHolderStrategy.class.getName());
    }

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().permitAll();
                /*.and()
                .authorizeRequests().antMatchers(LOGIN_URL).permitAll();*/ // TODO
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring().antMatchers("/VAADIN/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
