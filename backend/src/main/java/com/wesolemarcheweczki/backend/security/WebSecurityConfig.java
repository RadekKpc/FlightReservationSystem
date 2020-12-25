package com.wesolemarcheweczki.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    ClientDetailsService detailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/client").permitAll()
                .antMatchers("/api/carrier").hasAuthority(("ROLE_ADMIN"))
                .antMatchers("api/location").hasAuthority(("ROLE_ADMIN"))
                .antMatchers("api/location").hasAuthority(("ROLE_ADMIN"))
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();

    }
}