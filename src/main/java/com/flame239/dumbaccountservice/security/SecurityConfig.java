package com.flame239.dumbaccountservice.security;

import com.flame239.dumbaccountservice.entities.CustomerDetails;
import com.flame239.dumbaccountservice.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomersRepository customersRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/login").permitAll()
                .mvcMatchers("/customers").hasRole("ADMIN")
                .mvcMatchers("/customers/@me").authenticated()
                .mvcMatchers("/customers/{id}/accounts/{accId}").access("@securityService.checkPermission(authentication,#id,#accId)")
                .mvcMatchers("/customers/{id}/**").access("@securityService.checkPermission(authentication,#id)")
                .and()
                .formLogin().defaultSuccessUrl("/customers/@me")
                .and()
                .logout()
                .and()
                // enable https
                .requiresChannel().anyRequest().requiresSecure()
                .and().csrf().disable()
        ;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService());
    }

    @Bean
    public UserDetailsService userService() {
        return login -> customersRepository.findByLogin(login).map(CustomerDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " doesn't exist."));
    }
}
