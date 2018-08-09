package com.kotlarz.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity( securedEnabled = true )
public class GlobalSecurityConfiguration
                extends GlobalMethodSecurityConfiguration
{
    static
    {
        SecurityContextHolder.setStrategyName( VaadinSessionSecurityContextHolderStrategy.class.getName() );
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure( AuthenticationManagerBuilder auth )
                    throws Exception
    {
        super.configure( auth );
        auth.userDetailsService( userDetailsService ).passwordEncoder( passwordEncoder() );
    }

    @Bean
    public AuthenticationManager authenticationManagerBean()
                    throws Exception
    {
        return authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
