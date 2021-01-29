package com.example.ZuulApiGateway.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RequestFilter myCorsFilter;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .addFilterBefore(myCorsFilter, ChannelProcessingFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                //decomentare per disabilitare controllo accessi
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().permitAll();
    }
}