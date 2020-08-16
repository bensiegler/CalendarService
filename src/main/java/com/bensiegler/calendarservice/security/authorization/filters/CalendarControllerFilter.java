package com.bensiegler.calendarservice.security.authorization.filters;

import com.bensiegler.calendarservice.security.authorization.AccessDecisionManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class CalendarControllerFilter extends WebSecurityConfigurerAdapter {
    @Autowired
    AccessDecisionManagerFactory accessDecisionManagerFactory;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .antMatcher("**/cal/")
    }
}
