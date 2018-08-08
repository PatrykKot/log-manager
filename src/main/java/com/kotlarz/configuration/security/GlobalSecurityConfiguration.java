package com.kotlarz.configuration.security;

import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

//@Configuration
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
   /* @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    static {
        SecurityContextHolder.setStrategyName(VaadinSessionSecurityContextHolderStrategy.class.getName());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManager();
    }*/
}
