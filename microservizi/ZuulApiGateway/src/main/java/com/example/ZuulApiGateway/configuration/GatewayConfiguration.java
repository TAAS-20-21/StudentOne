package com.example.ZuulApiGateway.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class GatewayConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/authentication/**")
                .permitAll()
                .antMatchers("/**")
                .permitAll();

    }
}
